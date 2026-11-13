USE atendimento_medico;

INSERT INTO usuarios (nome, email, senha, perfil, crm, especialidade, cpf, telefone)
SELECT 'Ana Costa', 'ana.secretaria@example.com', 'senha123', 'secretaria', NULL, NULL, NULL, NULL
WHERE NOT EXISTS (
  SELECT 1 FROM usuarios WHERE email = 'ana.secretaria@example.com'
);

INSERT INTO usuarios (nome, email, senha, perfil, crm, especialidade, cpf, telefone)
SELECT 'Carlos Mendes', 'carlos.medico@example.com', 'senha123', 'medico', 'CRM-PB-12345', 'Cardiologia', NULL, NULL
WHERE NOT EXISTS (
  SELECT 1 FROM usuarios WHERE email = 'carlos.medico@example.com'
);

INSERT INTO usuarios (nome, email, senha, perfil, crm, especialidade, cpf, telefone)
SELECT 'Mariana Lima', 'mariana.medica@example.com', 'senha123', 'medico', 'CRM-PB-67890', 'Dermatologia', NULL, NULL
WHERE NOT EXISTS (
  SELECT 1 FROM usuarios WHERE email = 'mariana.medica@example.com'
);

INSERT INTO usuarios (nome, email, senha, perfil, crm, especialidade, cpf, telefone)
SELECT 'João Silva', 'joao.paciente@example.com', 'senha123', 'paciente', NULL, NULL, '12345678901', '(83) 99911-2233'
WHERE NOT EXISTS (
  SELECT 1 FROM usuarios WHERE email = 'joao.paciente@example.com' OR cpf = '12345678901'
);

INSERT INTO usuarios (nome, email, senha, perfil, crm, especialidade, cpf, telefone)
SELECT 'Beatriz Souza', 'beatriz.paciente@example.com', 'senha123', 'paciente', NULL, NULL, '23456789012', '(83) 99922-3344'
WHERE NOT EXISTS (
  SELECT 1 FROM usuarios WHERE email = 'beatriz.paciente@example.com' OR cpf = '23456789012'
);

INSERT INTO usuarios (nome, email, senha, perfil, crm, especialidade, cpf, telefone)
SELECT 'Lucas Almeida', 'lucas.paciente@example.com', 'senha123', 'paciente', NULL, NULL, '34567890123', '(83) 99933-4455'
WHERE NOT EXISTS (
  SELECT 1 FROM usuarios WHERE email = 'lucas.paciente@example.com' OR cpf = '34567890123'
);

INSERT INTO consultas (paciente_id, medico_id, data_hora, status, observacoes)
SELECT p.id, m.id, '2030-11-16 09:00:00', 'agendada', NULL
FROM usuarios p JOIN usuarios m ON m.email = 'carlos.medico@example.com' AND m.perfil = 'medico'
WHERE p.email = 'joao.paciente@example.com' AND p.perfil = 'paciente'
  AND NOT EXISTS (SELECT 1 FROM consultas c WHERE c.medico_id = m.id AND c.data_hora = '2030-11-16 09:00:00');

INSERT INTO consultas (paciente_id, medico_id, data_hora, status, observacoes)
SELECT p.id, m.id, '2030-11-16 10:00:00', 'agendada', NULL
FROM usuarios p JOIN usuarios m ON m.email = 'mariana.medica@example.com' AND m.perfil = 'medico'
WHERE p.email = 'beatriz.paciente@example.com' AND p.perfil = 'paciente'
  AND NOT EXISTS (SELECT 1 FROM consultas c WHERE c.medico_id = m.id AND c.data_hora = '2030-11-16 10:00:00');

INSERT INTO consultas (paciente_id, medico_id, data_hora, status, observacoes)
SELECT p.id, m.id, '2030-11-17 09:30:00', 'agendada', NULL
FROM usuarios p JOIN usuarios m ON m.email = 'carlos.medico@example.com' AND m.perfil = 'medico'
WHERE p.email = 'lucas.paciente@example.com' AND p.perfil = 'paciente'
  AND NOT EXISTS (SELECT 1 FROM consultas c WHERE c.medico_id = m.id AND c.data_hora = '2030-11-17 09:30:00');

INSERT INTO consultas (paciente_id, medico_id, data_hora, status, observacoes)
SELECT p.id, m.id, '2030-11-18 14:00:00', 'realizada', 'Consulta de rotina; retorno recomendado em seis meses.'
FROM usuarios p JOIN usuarios m ON m.email = 'mariana.medica@example.com' AND m.perfil = 'medico'
WHERE p.email = 'joao.paciente@example.com' AND p.perfil = 'paciente'
  AND NOT EXISTS (SELECT 1 FROM consultas c WHERE c.medico_id = m.id AND c.data_hora = '2030-11-18 14:00:00');
