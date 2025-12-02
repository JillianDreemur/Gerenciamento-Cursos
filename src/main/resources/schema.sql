-- Tabela de Usuários
CREATE TABLE IF NOT EXISTS usuarios (
    id SERIAL PRIMARY KEY,
    login VARCHAR(50) UNIQUE NOT NULL,
    senha VARCHAR(255) NOT NULL,
    nome VARCHAR(100) NOT NULL,
    email VARCHAR(100),
    role VARCHAR(20) NOT NULL DEFAULT 'ALUNO'
);

-- Tabela de Alunos
CREATE TABLE IF NOT EXISTS alunos (
    id SERIAL PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    email VARCHAR(100),
    cpf VARCHAR(14) UNIQUE NOT NULL,
    telefone VARCHAR(20)
);

-- Tabela de Cursos
CREATE TABLE IF NOT EXISTS cursos (
    id SERIAL PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    descricao TEXT,
    carga_horaria INTEGER,
    data_inicio DATE,
    data_fim DATE,
    status VARCHAR(20) DEFAULT 'ATIVO'
);

-- Tabela de Notas
CREATE TABLE IF NOT EXISTS notas (
    id SERIAL PRIMARY KEY,
    aluno_id BIGINT NOT NULL,
    curso_id BIGINT NOT NULL,
    valor DOUBLE PRECISION NOT NULL,
    tipo_avaliacao VARCHAR(50),
    data_avaliacao TIMESTAMP,
    observacoes TEXT,
    FOREIGN KEY (aluno_id) REFERENCES alunos(id) ON DELETE CASCADE,
    FOREIGN KEY (curso_id) REFERENCES cursos(id) ON DELETE CASCADE
);

-- Tabela de Sessions
CREATE TABLE IF NOT EXISTS sessions (
    id SERIAL PRIMARY KEY,
    aluno_id BIGINT NOT NULL,
    curso_id BIGINT NOT NULL,
    data_sessao TIMESTAMP NOT NULL,
    conteudo TEXT,
    duracao_minutos INTEGER,
    status VARCHAR(20) DEFAULT 'AGENDADA',
    FOREIGN KEY (aluno_id) REFERENCES alunos(id) ON DELETE CASCADE,
    FOREIGN KEY (curso_id) REFERENCES cursos(id) ON DELETE CASCADE
);

