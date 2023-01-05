<!DOCTYPE html>
<html>
  <head>
    <meta charset="utf-8"/>
    <link rel="stylesheet" href="/css/tacit-css-1.5.5.min.css" media="screen"/>
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
            ${ss.entry}
            <tr>
              <td>${s.id}</td>
              <td>${s.placa}</td>
              <td>${s.dataHora}</td>
            </tr>
            ${ss.end}
          </tbody>
        </table>
      </article>
    </section>
  </body>
</html>
