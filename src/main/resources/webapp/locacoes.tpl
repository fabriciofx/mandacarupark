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
      <form action="/locacoes" method="post">
        <label>Início</label>
        <input name="inicio" id="inicio" type="datetime-local" />
        <label>Término</label>
        <input name="termino" id="termino" type="datetime-local" />
        <button type="submit">Filtrar</button>
      </form>
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
              <td>${placa}</td>
              <td>${entrada}</td>
              <td>${saida}</td>
              <td>${valor}</td>
            </tr>
            ${ls.end}
          </tbody>
        </table>
      </article>
    </section>
  </body>
</html>
