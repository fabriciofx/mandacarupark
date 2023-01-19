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
            <td>Data/Hora</td>
            <td>Valor</td>
          </thead>
          <tbody>
            ${ps.entry}
            <tr>
              <td>${id}</td>
              <td>${dataHora}</td>
              <td>${valor}</td>
            </tr>
            ${ps.end}
          </tbody>
        </table>
      </article>
    </section>
  </body>
</html>
