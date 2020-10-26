/**
 * Get array of row objects from string table data
 * @param {String} data
 * @return {Object[]}
 * */

const getTableData = (data) => {
  const stringTable = data.trimRight().split('\n')
    .map((row) => row.split('\t'));
  const header = stringTable[0];
  const body = stringTable.slice(1);
  return body.map((row) => {
    const obj = {};
    row.forEach((cell, index) => {
      obj[header[index]] = cell;
    });
    return obj;
  });
};

export default getTableData;
