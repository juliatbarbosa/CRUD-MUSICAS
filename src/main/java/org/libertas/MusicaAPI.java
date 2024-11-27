package org.libertas;

import java.io.IOException;
import java.util.stream.Collectors;
import com.google.gson.Gson;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/MusicaAPI/*")
public class MusicaAPI extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public MusicaAPI() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        MusicaDAO mdao = new MusicaDAO();
        Gson gson = new Gson();
        String parametroNome = request.getParameter("nome");
        int id = 0;
        try {
        	id = Integer.parseInt(request.getPathInfo().substring(1));
        } catch (Exception e) {
        	e.printStackTrace();
        }
        
        String resposta = id > 0 ? gson.toJson(mdao.consultar(id)) : gson.toJson(mdao.listar(parametroNome));
        response.setContentType("application/json");
        response.getWriter().print(resposta);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String body = request.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
        Gson gson = new Gson();
        Musica m = gson.fromJson(body, Musica.class);

        MusicaDAO mdao = new MusicaDAO();
        boolean success = mdao.inserir(m);
        String message = success ? "Música cadastrada com sucesso!" : "Falha ao cadastrar!";
        
        Retorno retorno = new Retorno(success, message);
        response.setContentType("application/json");
        response.getWriter().print(gson.toJson(retorno));
    }

    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String body = request.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
        Gson gson = new Gson();
        Musica m = gson.fromJson(body, Musica.class);

        int id = 0;
        try {
            id = Integer.parseInt(request.getPathInfo().substring(1));
        } catch (Exception e) {
            e.printStackTrace();
        }
        m.setId(id);

        MusicaDAO mdao = new MusicaDAO();
        boolean success = mdao.alterar(m);
        String message = success ? "Música alterada com sucesso!" : "Falha ao alterar!";
        
        Retorno retorno = new Retorno(success, message);
        response.setContentType("application/json");
        response.getWriter().print(gson.toJson(retorno));
    }

    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        MusicaDAO mdao = new MusicaDAO();
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

        Musica m = new Musica();
        m.setId(id);
        
        boolean success = mdao.excluir(m);
        String message = success ? "Música excluída com sucesso!" : "Falha ao excluir!";
        
        Retorno retorno = new Retorno(success, message);
        response.setContentType("application/json");
        response.getWriter().print(gson.toJson(retorno));
    }
}
