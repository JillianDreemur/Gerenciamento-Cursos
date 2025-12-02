-- Inserir usuário admin padrão (senha: admin123)
-- Usando senha em texto plano para facilitar login (em produção usar BCrypt)
INSERT INTO usuarios (login, senha, nome, email, role) 
VALUES ('admin', 'admin123', 'Administrador', 'admin@example.com', 'ADMIN')
ON CONFLICT (login) DO NOTHING;

-- Inserir usuário professor padrão (senha: prof123)
INSERT INTO usuarios (login, senha, nome, email, role) 
VALUES ('professor', 'prof123', 'Professor Teste', 'professor@example.com', 'PROFESSOR')
ON CONFLICT (login) DO NOTHING;

-- Inserir alguns alunos de exemplo
INSERT INTO alunos (nome, email, cpf, telefone) 
VALUES 
    ('João Silva', 'joao@example.com', '123.456.789-00', '(11) 99999-9999'),
    ('Maria Santos', 'maria@example.com', '987.654.321-00', '(11) 88888-8888')
ON CONFLICT (cpf) DO NOTHING;

-- Inserir alguns cursos de exemplo
INSERT INTO cursos (nome, descricao, carga_horaria, data_inicio, data_fim, status) 
VALUES 
    ('Java Básico', 'Curso introdutório de Java', 40, CURRENT_DATE, CURRENT_DATE + INTERVAL '2 months', 'ATIVO'),
    ('Spring Boot', 'Curso avançado de Spring Boot', 60, CURRENT_DATE, CURRENT_DATE + INTERVAL '3 months', 'ATIVO');

