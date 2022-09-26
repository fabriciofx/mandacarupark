/*
 * The MIT License (MIT)
 *
 * Copyright (C) 2022 Fabrício Barros Cabral
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included
 * in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NON-INFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package com.github.fabriciofx.mandacarupark.web;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class ServerWeb {
    private final int port;

    public ServerWeb() {
        this(8080);
    }

    public ServerWeb(final int port) {
        this.port = port;
    }

    public void start() throws Exception {
        try (final ServerSocket socket = new ServerSocket(this.port)) {
            while (true) {
                try (final Socket client = socket.accept()) {
                    handleClient(client);
                }
            }
        }
    }

    private static void handleClient(final Socket client) throws IOException {
        final BufferedReader br = new BufferedReader(
            new InputStreamReader(client.getInputStream())
        );
        final StringBuilder requestBuilder = new StringBuilder();
        while (true) {
            final String line = br.readLine();
            if (line.isBlank()) {
                break;
            }
            requestBuilder.append(line + "\r\n");
        }
        final String request = requestBuilder.toString();
        final String[] lines = request.split("\r\n");
        final String[] parts = lines[0].split(" ");
        final String method = parts[0];
        final String path = parts[1];
        final String version = parts[2];
        final String host = lines[1].split(" ")[1];
        final List<String> headers = new ArrayList<>();
        for (int hdr = 2; hdr < lines.length; hdr++) {
            String header = lines[hdr];
            headers.add(header);
        }
        final String log = String.format(
            "Client %s, method %s, path %s, version %s, host %s, headers %s",
            client.toString(),
            method,
            path,
            version,
            host,
            headers.toString()
        );
        System.out.println(log);
        final Path filePath = getFilePath(path);
        if (Files.exists(filePath)) {
            // file exist
            final String contentType = guessContentType(filePath);
            sendResponse(
                client,
                "200 OK",
                contentType,
                Files.readAllBytes(filePath)
            );
        } else {
            // 404
            byte[] notFoundContent = "<h1>Not found :(</h1>".getBytes();
            sendResponse(client, "404 Not Found", "text/html", notFoundContent);
        }
    }

    private static void sendResponse(
        final Socket client,
        final String status,
        final String contentType,
        final byte[] content
    ) throws IOException {
        final OutputStream out = client.getOutputStream();
        out.write(("HTTP/1.1 \r\n" + status).getBytes());
        out.write(("ContentType: " + contentType + "\r\n").getBytes());
        out.write("\r\n".getBytes());
        out.write(content);
        out.write("\r\n\r\n".getBytes());
        out.flush();
        client.close();
    }

    private static Path getFilePath(final String filename) {
        final Path path;
        if ("/".equals(filename)) {
            path = Paths.get("/tmp/www", "/index.html");
        } else {
            path = Paths.get("/tmp/www", filename);
        }
        return path;
    }

    private static String guessContentType(final Path filePath)
        throws IOException {
        return Files.probeContentType(filePath);
    }
}