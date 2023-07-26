<!DOCTYPE html>
<html lang="pt-br">
  <head>
    <meta charset="utf-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    <link rel="stylesheet" href="css/tacit-css-1.5.5.min.css" media="screen"/>
    <link rel="stylesheet" href="css/styles.css" media="screen"/>
    <title>MandacaruPark</title>
  </head>
  <body>
    <section>
      ${header}
      <form action="/locacoes" method="get">
        <label for="inicio">Início</label>
        <input type="datetime-local" id="inicio" name="inicio" value="2023-01-01T00:00" required />
        <label for="termino">Término</label>
        <input type="datetime-local" id="termino" name="termino" value="2023-12-31T23:59" required />
        <button type="submit">Filtrar</button>
      </form>
      <article>
        <table>
          <thead>
            <td>Placa</td>
            <td>Entrada</td>
            <td>Saída</td>
            <td>Permanência</td>
            <td>Valor</td>
          </thead>
          <tbody>
            ${ls.begin}
            <tr>
              <td>${placa}</td>
              <td>${entrada}</td>
              <td>${saida}</td>
              <td>${permanencia}</td>
              <td>${valor}</td>
            </tr>
            ${ls.end}
          </tbody>
        </table>
        ${pagination}
      </article>
    </section>
  </body>
</html>
