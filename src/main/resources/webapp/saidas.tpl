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
      <article>
        <table>
          <thead>
            <td>Id</td>
            <td>Placa</td>
            <td>Data/Hora</td>
          </thead>
          <tbody>
            ${ss.begin}
            <tr>
              <td>${id}</td>
              <td>${placa}</td>
              <td>${dataHora}</td>
            </tr>
            ${ss.end}
          </tbody>
        </table>
        ${pagination}
      </article>
    </section>
  </body>
</html>
