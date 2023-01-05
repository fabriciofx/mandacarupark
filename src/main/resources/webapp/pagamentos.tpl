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
            <td>Data/Hora</td>
            <td>Valor</td>
          </thead>
          <tbody>
            ${ps.entry}
            <tr>
              <td>${p.id}</td>
              <td>${p.dataHora}</td>
              <td>${p.valor}</td>
            </tr>
            ${ps.end}
          </tbody>
        </table>
      </article>
    </section>
  </body>
</html>
