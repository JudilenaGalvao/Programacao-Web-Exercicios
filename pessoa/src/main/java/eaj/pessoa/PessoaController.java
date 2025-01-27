package eaj.pessoa;

import java.io.IOException;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Controller
public class PessoaController {

    @RequestMapping( method = RequestMethod.GET, value = "/cadastrar")
    public void MostrarPessoa(HttpServletRequest request, HttpServletResponse response) throws IOException{
        response.getWriter().println("Teste");

    }
    
    @RequestMapping(method = RequestMethod.GET, value = "/doEditarPage")
    public void doEditarPage(HttpServletRequest request, HttpServletResponse response) throws IOException{
        var id = Integer.parseInt(request.getParameter("id"));
        var pDAO = new PessoaDAO();
        response.setContentType("text/HTML");

        Pessoa p = pDAO.getPessoaById(id);

        var writer = response.getWriter();

        writer.println("<html>" +
                    "<body>" +
                    "<form action='doAtualizar' method='post'>");
            writer.println("<input type='hidden' name='id' value='"+p.getId()+"'>");
            writer.println("<input type='text' name='nome' value='"+p.getNome()+"'>");
            writer.println("<input type='number' name='idade' value='"+p.getIdade()+"'>");

            writer.println("<button type='submit'>Enviar</button");
            writer.println("</form>" +
            "</body>" +
            "<html>");
    }   

    @RequestMapping( method = RequestMethod.GET, value = "/doExcluir")
    public void excluirPessoa(HttpServletRequest request, HttpServletResponse response) throws IOException{
        var p = new Pessoa();
        // var nome = request.getParameter("nome");
        //var idade = Integer.parseInt(request.getParameter("idade"));
        var id = Integer.parseInt(request.getParameter("id"));

       // p.setNome(nome);
        //p.setIdade(idade);
        p.setId(id);
        
        PessoaDAO pDAO = new PessoaDAO();

        pDAO.excluirPessoa(p);
        response.setContentType("text/HTML");
        var writer = response.getWriter();
        writer.println("Excluido com sucesso");
    }

    @RequestMapping( method = RequestMethod.POST, value = "/doAtualizar")
    public void doAtualizar(HttpServletRequest request, HttpServletResponse response) throws IOException{
        
        var nome = request.getParameter("nome");
        var idade = Integer.parseInt(request.getParameter("idade"));
        var id = Integer.parseInt(request.getParameter("id"));

        var p = new Pessoa(id, nome, idade);

        PessoaDAO pDAO = new PessoaDAO();

        pDAO.alterarPessoa(p);
        

    }

    @RequestMapping( method = RequestMethod.POST, value = "/cadastrar")
    public void cadastraPessoa(HttpServletRequest request, HttpServletResponse response) throws IOException{
        var p = new Pessoa();
        var nome = request.getParameter("nome");
        var idade = Integer.parseInt(request.getParameter("idade"));

        p.setNome(nome);
        p.setIdade(idade);

        PessoaDAO pDAO = new PessoaDAO();

        pDAO.cadastrarPessoa(p);
        response.setContentType("text/HTML");

        var writer = response.getWriter();

        writer.println("Cadastro feito com sucesso!");

        //writer.println("Verifique se atualizou o banco");

    }

    @RequestMapping(method = RequestMethod.GET, value = "/doListar")
    public void listarPessoas(HttpServletRequest request, HttpServletResponse response) throws IOException{
        var pDAO = new PessoaDAO();
        var writer = response.getWriter();
        response.setContentType("text/HTML");

        var listarPessoas = pDAO.listarPessoas();

        for(var t1:listarPessoas){
            writer.println("<h2>Nome: " + t1.getNome() + "</h2>");
            writer.println("<p> Idade: " + t1.getIdade() + "</p> </hr>");
            writer.println("<a href='doEditarPage?id="+t1.getId()+"'>Editar</a>");
            writer.println("<a href='doExcluir?id="+t1.getId()+"'>Excluir</a>");
           
        }
        

    }

    @RequestMapping(value = "/doBuscar", method = RequestMethod.POST)
        public void buscarPessoa(HttpServletRequest request, HttpServletResponse response) throws IOException {
            var id = Integer.parseInt(request.getParameter("id"));

            var pDAO = new PessoaDAO();

            Pessoa p = pDAO.getPessoaById(id);
            response.setContentType("text/HTML");

            var writer = response.getWriter();
            writer.println("<html>");
            writer.println("<body>");
            if (p != null){
                writer.println("<h2>Nome: " + p.getNome() + "</h2>");
            writer.println("<p> Idade: " + p.getIdade() + "</p> </hr>");
            }else{
                writer.println("<p> Não encontrado </p>");
            }
            writer.println("</body>");
            writer.println("</html>");
        }

}
