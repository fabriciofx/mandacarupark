<!DOCTYPE html>
<html>
  <head>
    <meta charset="utf-8"/>
    <link rel="stylesheet" href="/css/tacit-css-1.5.5.min.css" media="screen"/>
    <title>MandacaruPark</title>
  </head>
  <body>
    <section>
      <header>
        <nav>
          <button onclick="location.href='http://localhost:8080/entrada.html'">
            Entrada
          </button>
          <button onclick="location.href='http://localhost:8080/saida.html'">
            Saída
          </button>
          <button onclick="location.href='http://localhost:8080/entradas'">
            Entradas
          </button>
          <button onclick="location.href='http://localhost:8080/saidas'">
            Saídas
          </button>
          <button onclick="location.href='http://localhost:8080/pagamentos'">
            Pagamentos
          </button>
          <button onclick="location.href='http://localhost:8080/locacoes'">
            Locações
          </button>
        </nav>
      </header>
      <article>
        <table>
          <thead>
            <td>Id</td>
            <td>Placa</td>
            <td>Data/Hora</td>
          </thead>
          <tbody>
            ${es.entry}
            <tr>
              <td>${e.id}</td>
              <td>${e.placa}</td>
              <td>${e.dataHora}</td>
            </tr>
            ${es.end}
          </tbody>
        </table>
      </article>
    </section>
  </body>
</html>
