import moment from 'moment';

const types = {
  t: {
    pgtype: 'TEXT',
    parse: (val) => `'${val}'`,
  },
  d: {
    pgtype: 'DATE',
    parse: (val) => `'${moment(val, 'DD.MM.YYYY').format('YYYY-MM-DD')}'`,
  },
  n: {
    pgtype: 'NUMERIC(40,5)',
    parse: (val) => val,
  },
  f: {
    pgtype: 'REAL',
    parse: (val) => val,
  },
};

const generateSQL = (
  inputString,
  tableName,
  dropTable = true,
  createTable = true,
  addId = true,
) => {
  const stringTable = inputString
    .trimRight()
    .split('\n')
    .map((row) => row.split('\t'));
  const colNames = stringTable[0];
  const colTypes = stringTable[1];
  const body = stringTable.slice(2, stringTable.length);
  const drop = `DROP TABLE IF EXISTS ${tableName};\n`;
  const tempCr = colTypes
    .map((col, index) => `${colNames[index]} ${types[col].pgtype}`)
    .join(',\n  ');
  const create = `CREATE TABLE IF NOT EXISTS ${tableName}
(${addId ? '\n  id BIGSERIAL PRIMARY KEY,' : ''}
  ${tempCr}
);\n`;

  const insertColumns = colNames.join(', ');
  const insertData = body
    .map((row, i) => {
      const rowData = row
        .map((cell, j) => (cell === '' ? 'NULL' : types[colTypes[j]].parse(cell)))
        .join(', ');
      return `(${addId ? `${i + 1}, ` : ''}${rowData})`;
    })
    .join(',\n  ');

  const insert = `INSERT INTO ${tableName} (${
    addId ? 'id, ' : ''
  }${insertColumns}) VALUES
  ${insertData};`;
  return `${dropTable ? drop : ''}${createTable ? create : ''}${insert}`;
};

export default generateSQL;
