<!DOCTYPE html>
<html>
  <head>
    <meta charset="utf-8">
    <link rel="stylesheet" href="/css/tacit-css-1.5.5.min.css" media="screen">
    <title>DesktopWeb</title>
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
            <td>Placa</td>
            <td>Entrada</td>
            <td>Saída</td>
            <td>Valor</td>
          </thead>
          <tbody>
            ${locacoes}
          </tbody>
        </table>
      </article>
    </section>
  </body>
</html>
