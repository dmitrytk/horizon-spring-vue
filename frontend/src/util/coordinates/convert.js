import {
  convertTable,
  coordRegexes,
  decimalToDegreeTable,
  degreeToDecimalTable,
  StringToDecimalTable,
} from './converterUtils';
import systems from './systems';

/**
 * Convert input String
 * @param {String} inType
 * @param {String} inSystem
 * @param {String} inZone
 * @param {String} inValue input string value
 * @param {String} outType
 * @param {String} outSystem
 * @param {String} outZone
 * @param {String} outDegreeType
 * @returns {Object[][]}
 */
const convert = (
  inType,
  inSystem,
  inZone,
  inValue,
  outType,
  outSystem,
  outZone,
  outDegreeType,
) => {
  const stringTable = inValue
    .split('\n')
    .map((row) => row.split('\t'))
    .filter((row) => row.length === 3);

  let coordTable;
  let fromProjection;
  let toProjection;
  let result;

  if (inType === 'Геодезическая') {
    fromProjection = systems[inType][inSystem];
    const inDegreeType = coordRegexes.decimal.test(stringTable[0].join('\t'))
      ? 'decimal'
      : 'degree';
    coordTable = inDegreeType === 'decimal'
      ? StringToDecimalTable(stringTable)
      : degreeToDecimalTable(stringTable);
  } else {
    coordTable = StringToDecimalTable(stringTable);
    fromProjection = systems[inType][inSystem][inZone];
  }
  if (outType === 'Геодезическая') {
    toProjection = systems[outType][outSystem];

    result = convertTable(coordTable, fromProjection, toProjection);
    if (outDegreeType === 'ГГ MM СС.с') {
      result = decimalToDegreeTable(result);
    }
  } else {
    toProjection = systems[outType][outSystem][outZone];
    result = convertTable(coordTable, fromProjection, toProjection);
  }
  if (result) {
    return result.map((row) => row.join('\t')).join('\n');
  }

  return result;
};

export default convert;
