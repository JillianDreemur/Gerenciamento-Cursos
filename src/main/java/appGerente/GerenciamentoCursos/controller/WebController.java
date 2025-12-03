package appGerente.GerenciamentoCursos.controller;

import appGerente.GerenciamentoCursos.models.*;
import appGerente.GerenciamentoCursos.services.*;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.util.List;

@Controller
public class WebController {

    @Autowired
    private AlunoService alunoService;

    @Autowired
    private CursoService cursoService;

    @Autowired
    private NotaService notaService;

    @Autowired
    private SessionService sessionService;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/")
    public String index() {
        return "redirect:/login";
    }

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    @PostMapping("/login")
    public String login(@RequestParam String login, @RequestParam String senha, 
                       HttpSession session, RedirectAttributes redirectAttributes) {
        try {
            Usuario usuario = usuarioService.findByLogin(login);
            // Verificação de senha com BCrypt
            if (passwordEncoder.matches(senha, usuario.getSenha()) || usuario.getSenha().equals(senha)) {
                session.setAttribute("usuario", usuario);
                session.setAttribute("login", usuario.getLogin());
                session.setAttribute("nome", usuario.getNome());
                session.setAttribute("role", usuario.getRole());
                return "redirect:/dashboard";
            } else {
                redirectAttributes.addFlashAttribute("error", "Credenciais inválidas");
                return "redirect:/login";
            }
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Usuário não encontrado");
            return "redirect:/login";
        }
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }

    @GetMapping("/dashboard")
    public String dashboard(Model model, HttpSession session) {
        if (session.getAttribute("usuario") == null) {
            return "redirect:/login";
        }
        model.addAttribute("usuario", session.getAttribute("usuario"));
        return "dashboard";
    }

    // Alunos
    @GetMapping("/alunos")
    public String listarAlunos(Model model, HttpSession session) {
        if (session.getAttribute("usuario") == null) {
            return "redirect:/login";
        }
        List<Aluno> alunos = alunoService.findAll();
        model.addAttribute("alunos", alunos);
        return "alunos/list";
    }

    @GetMapping("/alunos/novo")
    public String novoAluno(Model model, HttpSession session) {
        if (session.getAttribute("usuario") == null) {
            return "redirect:/login";
        }
        model.addAttribute("aluno", new Aluno());
        return "alunos/form";
    }

    @PostMapping("/alunos")
    public String salvarAluno(@ModelAttribute Aluno aluno, RedirectAttributes redirectAttributes) {
        try {
            alunoService.save(aluno);
            redirectAttributes.addFlashAttribute("success", "Aluno salvo com sucesso!");
            return "redirect:/alunos";
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/alunos/novo";
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/alunos/novo";
        }
    }

    @GetMapping("/alunos/{id}/editar")
    public String editarAluno(@PathVariable Long id, Model model, HttpSession session) {
        if (session.getAttribute("usuario") == null) {
            return "redirect:/login";
        }
        Aluno aluno = alunoService.findById(id);
        model.addAttribute("aluno", aluno);
        return "alunos/form";
    }

    @PostMapping("/alunos/{id}")
    public String atualizarAluno(@PathVariable Long id, @ModelAttribute Aluno aluno, RedirectAttributes redirectAttributes) {
        try {
            alunoService.update(id, aluno);
            redirectAttributes.addFlashAttribute("success", "Aluno atualizado com sucesso!");
            return "redirect:/alunos";
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/alunos/" + id + "/editar";
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/alunos/" + id + "/editar";
        }
    }

    @GetMapping("/alunos/{id}/deletar")
    public String deletarAluno(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        alunoService.deleteById(id);
        redirectAttributes.addFlashAttribute("success", "Aluno deletado com sucesso!");
        return "redirect:/alunos";
    }

    // Cursos
    @GetMapping("/cursos")
    public String listarCursos(Model model, HttpSession session) {
        if (session.getAttribute("usuario") == null) {
            return "redirect:/login";
        }
        List<Curso> cursos = cursoService.findAll();
        model.addAttribute("cursos", cursos);
        return "cursos/list";
    }

    @GetMapping("/cursos/novo")
    public String novoCurso(Model model, HttpSession session) {
        if (session.getAttribute("usuario") == null) {
            return "redirect:/login";
        }
        model.addAttribute("curso", new Curso());
        model.addAttribute("dataAtual", LocalDate.now());
        model.addAttribute("dataMinima", LocalDate.of(2010, 1, 1));
        return "cursos/form";
    }

    @PostMapping("/cursos")
    public String salvarCurso(@ModelAttribute Curso curso, RedirectAttributes redirectAttributes) {
        try {
            cursoService.save(curso);
            redirectAttributes.addFlashAttribute("success", "Curso salvo com sucesso!");
            return "redirect:/cursos";
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/cursos/novo";
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/cursos/novo";
        }
    }

    @GetMapping("/cursos/{id}/editar")
    public String editarCurso(@PathVariable Long id, Model model, HttpSession session) {
        if (session.getAttribute("usuario") == null) {
            return "redirect:/login";
        }
        Curso curso = cursoService.findById(id);
        model.addAttribute("curso", curso);
        model.addAttribute("dataAtual", LocalDate.now());
        model.addAttribute("dataMinima", LocalDate.of(2010, 1, 1));
        return "cursos/form";
    }

    @PostMapping("/cursos/{id}")
    public String atualizarCurso(@PathVariable Long id, @ModelAttribute Curso curso, RedirectAttributes redirectAttributes) {
        try {
            cursoService.update(id, curso);
            redirectAttributes.addFlashAttribute("success", "Curso atualizado com sucesso!");
            return "redirect:/cursos";
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/cursos/" + id + "/editar";
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/cursos/" + id + "/editar";
        }
    }

    @GetMapping("/cursos/{id}/deletar")
    public String deletarCurso(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        cursoService.deleteById(id);
        redirectAttributes.addFlashAttribute("success", "Curso deletado com sucesso!");
        return "redirect:/cursos";
    }

    // Notas
    @GetMapping("/notas")
    public String listarNotas(Model model, HttpSession session) {
        if (session.getAttribute("usuario") == null) {
            return "redirect:/login";
        }
        List<Nota> notas = notaService.findAll();
        List<Aluno> alunos = alunoService.findAll();
        List<Curso> cursos = cursoService.findAll();
        model.addAttribute("notas", notas);
        model.addAttribute("alunos", alunos);
        model.addAttribute("cursos", cursos);
        return "notas/list";
    }

    @GetMapping("/notas/novo")
    public String novaNota(Model model, HttpSession session) {
        if (session.getAttribute("usuario") == null) {
            return "redirect:/login";
        }
        List<Aluno> alunos = alunoService.findAll();
        List<Curso> cursos = cursoService.findAll();
        model.addAttribute("nota", new Nota());
        model.addAttribute("alunos", alunos);
        model.addAttribute("cursos", cursos);
        return "notas/form";
    }

    @PostMapping("/notas")
    public String salvarNota(@ModelAttribute Nota nota, RedirectAttributes redirectAttributes) {
        try {
            notaService.save(nota);
            redirectAttributes.addFlashAttribute("success", "Nota salva com sucesso!");
            return "redirect:/notas";
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/notas/novo";
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/notas/novo";
        }
    }

    // Sessions
    @GetMapping("/sessions")
    public String listarSessions(Model model, HttpSession session) {
        if (session.getAttribute("usuario") == null) {
            return "redirect:/login";
        }
        List<Session> sessions = sessionService.findAll();
        List<Aluno> alunos = alunoService.findAll();
        List<Curso> cursos = cursoService.findAll();
        model.addAttribute("sessions", sessions);
        model.addAttribute("alunos", alunos);
        model.addAttribute("cursos", cursos);
        return "sessions/list";
    }

    @GetMapping("/sessions/novo")
    public String novaSession(Model model, HttpSession session) {
        if (session.getAttribute("usuario") == null) {
            return "redirect:/login";
        }
        List<Aluno> alunos = alunoService.findAll();
        List<Curso> cursos = cursoService.findAll();
        model.addAttribute("session", new Session());
        model.addAttribute("alunos", alunos);
        model.addAttribute("cursos", cursos);
        return "sessions/form";
    }

    @PostMapping("/sessions")
    public String salvarSession(@ModelAttribute Session session, RedirectAttributes redirectAttributes) {
        sessionService.save(session);
        redirectAttributes.addFlashAttribute("success", "Sessão salva com sucesso!");
        return "redirect:/sessions";
    }
}

