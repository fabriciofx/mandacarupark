<!DOCTYPE html>
<html>
  <head>
    <meta charset="utf-8"/>
    <link rel="stylesheet" href="/css/tacit-css-1.5.5.min.css" media="screen"/>
    <link rel="stylesheet" href="/css/styles.css" media="screen"/>
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
            ${es.begin}
            <tr>
              <td>${id}</td>
              <td>${placa}</td>
              <td>${dataHora}</td>
            </tr>
            ${es.end}
          </tbody>
        </table>
        ${footer}
      </article>
    </section>
  </body>
</html>
