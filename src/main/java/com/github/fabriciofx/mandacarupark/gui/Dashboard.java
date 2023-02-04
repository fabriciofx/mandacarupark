/*
 * The MIT License (MIT)
 *
 * Copyright (C) 2022-2023 Fabrício Barros Cabral
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
package com.github.fabriciofx.mandacarupark.gui;

import com.github.fabriciofx.mandacarupark.Entrada;
import com.github.fabriciofx.mandacarupark.Entradas;
import com.github.fabriciofx.mandacarupark.Server;
import com.github.fabriciofx.mandacarupark.db.RandomName;
import com.github.fabriciofx.mandacarupark.db.ScriptSql;
import com.github.fabriciofx.mandacarupark.server.ServerH2;
import com.github.fabriciofx.mandacarupark.db.Session;
import com.github.fabriciofx.mandacarupark.db.ds.H2Memory;
import com.github.fabriciofx.mandacarupark.db.session.NoAuth;
import com.github.fabriciofx.mandacarupark.entradas.EntradasSql;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.MapValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.StringConverter;
import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("unchecked")
public final class Dashboard extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        Scene scene = new Scene(new Group(), 600, 500);
        stage.setTitle("MandacaruPark");

        final Label label = new Label("Entradas");
        label.setFont(new Font("Arial", 20));

        TableColumn<Map, String> coluna1 = new TableColumn<>("Id");
        coluna1.setCellValueFactory(new MapValueFactory("id"));
        coluna1.setMinWidth(250);

        TableColumn<Map, String> coluna2 = new TableColumn<>("Placa");
        coluna2.setCellValueFactory(new MapValueFactory("placa"));
        coluna2.setMinWidth(130);

        TableColumn<Map, String> coluna3 = new TableColumn<>("Data/Hora");
        coluna3.setCellValueFactory(new MapValueFactory("dataHora"));
        coluna3.setMinWidth(130);

        TableView tableView = new TableView<>(generateDataInMap());
        tableView.prefHeightProperty().bind(stage.heightProperty());
        tableView.prefWidthProperty().bind(stage.widthProperty());
        tableView.setEditable(true);
        tableView.getSelectionModel().setCellSelectionEnabled(true);
        tableView.getColumns().setAll(coluna1, coluna2, coluna3);

        Callback<TableColumn<Map, String>, TableCell<Map, String>>
            cellFactoryForMap = p -> new TextFieldTableCell(
            new StringConverter() {
                @Override
                public String toString(Object t) {
                    return t.toString();
                }

                @Override
                public Object fromString(String string) {
                    return string;
                }
            });
        coluna1.setCellFactory(cellFactoryForMap);
        coluna2.setCellFactory(cellFactoryForMap);
        coluna3.setCellFactory(cellFactoryForMap);

        final VBox vbox = new VBox();
        vbox.setSpacing(5);
        vbox.setPadding(new Insets(10, 0, 0, 10));
        vbox.getChildren().addAll(label, tableView);
        ((Group) scene.getRoot()).getChildren().addAll(vbox);
        stage.setScene(scene);
        stage.show();
    }

    private ObservableList<Map> generateDataInMap() {
        final ObservableList<Map> linhas = FXCollections.observableArrayList();
        final Session session = new NoAuth(new H2Memory(new RandomName()));
        try (
            final Server server = new ServerH2(
                session,
                new ScriptSql("mandacarupark.sql")
            )
        ) {
            server.start();
            final Entradas entradas = new EntradasSql(session);
            for (final Entrada entrada: entradas) {
                final Map<String, String> linha = new HashMap<>();
                linha.put(
                    "id",
                    entrada.id().toString()
                );
                linha.put(
                    "placa",
                    entrada.sobre().get("placa").toString()
                );
                linha.put(
                    "dataHora",
                    entrada.sobre().get("dataHora").toString()
                );
                linhas.add(linha);
            }
        } catch (final Exception ex) {
            throw new RuntimeException(ex);
        }
        return linhas;
    }
}
