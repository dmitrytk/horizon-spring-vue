import proj4 from 'proj4';
import round from '../number';

const coordRegexes = {
  decimal: /^\w+\t\d{1,3}\.?\d*\t\d{1,3}\.?\d*$/,
  square: /^\w+\t\d{4,}\.?\d*\t\d\d{4,}\.?\d*$/,
  degree: /^\w+\t\d{1,2} \d{1,2} \d{1,2}\.?\d*\t\d{1,2} \d{1,2} \d{1,2}\.?\d*$/,
};

/**
 * `60 10 20.05`   ->   {deg:60, min:10, sec:20.05}
 * @param {String} data
 * @returns {Object}
 */
const stringToDegree = (data) => {
  const arr = data.trim().split(' ');
  return {
    deg: Number(arr[0]),
    min: Number(arr[1]),
    sec: Number(arr[2]),
  };
};

/**
 * {deg:60, min:10, sec:20.05}   ->   `60 10 20.05`
 * @param {Object} degree
 * @returns {String}
 */
const degreeToString = (degree) => `${degree.deg} ${degree.min} ${degree.sec}`;

/**
 * {deg:60, min:10, sec:20.05}   ->   60.1546565
 * @param {Object} degrees
 * @returns {Number}
 */
const degreeToDecimal = (degrees) => {
  const isNegative = degrees.deg < 0;
  if (isNegative) {
    return degrees.deg - degrees.min / 60 - degrees.sec / 3600;
  }
  return degrees.deg + degrees.min / 60 + degrees.sec / 3600;
};

/**
 * 60.1546565   ->   {deg:60, min:10, sec:20.05}
 * @param {Number} dec
 * @returns {Object}
 */
const decimalToDegree = (dec) => {
  const isNegative = dec < 0;
  const deg = isNegative ? Math.ceil(dec) : Math.floor(dec);
  const min = isNegative
    ? Math.floor((deg - dec) * 60)
    : Math.floor((dec - deg) * 60);
  const sec = isNegative
    ? round((deg - dec - min / 60) * 3600, 2)
    : round((dec - deg - min / 60) * 3600, 2);
  return { deg, min, sec };
};

/**
 * Point  Latitude Longitude
 * 12R    60 10 10 70 15 20 -> 12R  60.454 70.4545
 * @param {Object[][]} table
 * @returns {Object[][]}
 */
const degreeToDecimalTable = (table) => table.map((row) => [
  row[0],
  degreeToDecimal(stringToDegree(row[1])),
  degreeToDecimal(stringToDegree(row[2])),
]);

/**
 * Convert String coordinates to Number
 * Point    Latitude    Longitude
 * 12R      60.454      70.4545 ->  12R    60.454    70.4545
 * @param {Object[][]} table
 * @returns {Object[][]}
 */
// eslint-disable-next-line max-len
const StringToDecimalTable = (table) => table.map((row) => [row[0], Number(row[1]), Number(row[2])]);

/**
 * Point    Longitude   Latitude
 * 12R      60.454      70.4545    ->    12R  60 10 10    70 15 20
 * @param {Object[][]} table
 * @returns {Object[][]}
 */
const decimalToDegreeTable = (table) => table.map((row) => [
  row[0],
  degreeToString(decimalToDegree(row[1])),
  degreeToString(decimalToDegree(row[2])),
]);

/**
 * Convert coordinate table between selected projections
 * Point    Latitude    Longitude
 * 12R      60.454      70.4545    ->   12R  60 10 10    70 15 20
 * 13R      60.486      70.798    ->   13R  60 15 20    70 18 36
 * reverse X and Y coordinates if long/lat projection
 * @param {Object[][]} table
 * @param {String} fromProjection
 * @param {String} toProjection
 */
const convertTable = (table, fromProjection, toProjection) => {
  const fromLongLat = fromProjection.includes('longlat');
  const toLongLat = toProjection.includes('longlat');
  return table.map((row) => {
    const [x, y] = fromLongLat ? [row[2], row[1]] : [row[1], row[2]];
    const [resultX, resultY] = toLongLat
      ? proj4(fromProjection, toProjection, [x, y]).reverse()
      : proj4(fromProjection, toProjection, [x, y]);
    return [row[0], resultX, resultY];
  });
};

export {
  stringToDegree,
  degreeToString,
  degreeToDecimal,
  decimalToDegree,
  coordRegexes,
  degreeToDecimalTable,
  StringToDecimalTable,
  decimalToDegreeTable,
  convertTable,
};
