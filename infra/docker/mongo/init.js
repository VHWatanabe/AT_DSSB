const dbName = 'at_dssb';
const db = connect('mongodb://localhost:27017/' + dbName);

db.createCollection('alunos');
db.createCollection('disciplinas');
db.createCollection('matriculas');

db.alunos.createIndex({ cpf: 1 }, { unique: true });
db.disciplinas.createIndex({ codigo: 1 }, { unique: true });
