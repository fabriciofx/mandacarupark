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
            ${ss.entry}
            <tr>
              <td>${id}</td>
              <td>${placa}</td>
              <td>${dataHora}</td>
            </tr>
            ${ss.end}
          </tbody>
        </table>
        <div class="center">
          <a href="http://localhost:8080/saidas/?page=1">&lt; Anterior</a>
          <a href="http://localhost:8080/saidas/?page=1">1</a>
          <a href="http://localhost:8080/saidas/?page=2">2</a>
          <a href="http://localhost:8080/saidas/?page=3">3</a>
          ...
          <a href="http://localhost:8080/saidas/?page=6">6</a>
          <a href="http://localhost:8080/saidas/?page=1">Próximo &gt;</a>
        </div>
      </article>
    </section>
  </body>
</html>
