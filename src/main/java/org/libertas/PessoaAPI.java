package org.libertas;

import java.io.IOException;
import java.util.stream.Collectors;
import com.google.gson.Gson;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/PessoaAPI/*")
public class PessoaAPI extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public PessoaAPI() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PessoaDAO pdao = new PessoaDAO();
        Gson gson = new Gson();
        String resposta = gson.toJson(pdao.listar());
        response.setContentType("application/json");
        response.getWriter().print(resposta);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String body = request.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
        Gson gson = new Gson();
        Pessoa p = gson.fromJson(body, Pessoa.class);

        PessoaDAO pdao = new PessoaDAO();
        boolean success = pdao.inserir(p);
        String message = success ? "Inserido com sucesso!" : "Falha ao inserir!";
        
        Retorno retorno = new Retorno(success, message);
        response.setContentType("application/json");
        response.getWriter().print(gson.toJson(retorno));
    }

    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String body = request.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
        Gson gson = new Gson();
        Pessoa p = gson.fromJson(body, Pessoa.class);

        int id = 0;
        try {
            id = Integer.parseInt(request.getPathInfo().substring(1));
        } catch (Exception e) {
            e.printStackTrace();
        }
        p.setIdpessoa(id);

        PessoaDAO pdao = new PessoaDAO();
        boolean success = pdao.alterar(p);
        String message = success ? "Alterado com sucesso!" : "Falha ao alterar!";
        
        Retorno retorno = new Retorno(success, message);
        response.setContentType("application/json");
        response.getWriter().print(gson.toJson(retorno));
    }

    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PessoaDAO pdao = new PessoaDAO();
        Gson gson = new Gson();

        int id = 0;
        try {
            id = Integer.parseInt(request.getPathInfo().substring(1));
        } catch (Exception e) {
            String resp = "ID obrigatório!";
            response.setContentType("text/plain");
            response.getWriter().print(resp);
            return;
        }

        Pessoa p = new Pessoa();
        p.setIdpessoa(id);
        
        boolean success = pdao.excluir(p);
        String message = success ? "Excluído com sucesso!" : "Falha ao excluir!";
        
        Retorno retorno = new Retorno(success, message);
        response.setContentType("application/json");
        response.getWriter().print(gson.toJson(retorno));
    }
}
