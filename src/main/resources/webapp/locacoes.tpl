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
            <td>Placa</td>
            <td>Entrada</td>
            <td>Saída</td>
            <td>Valor</td>
          </thead>
          <tbody>
            ${ls.entry}
            <tr>
              <td>${l.placa}</td>
              <td>${l.entrada}</td>
              <td>${l.saida}</td>
              <td>${l.valor}</td>
            </tr>
            ${ls.end}
          </tbody>
        </table>
      </article>
    </section>
  </body>
</html>
