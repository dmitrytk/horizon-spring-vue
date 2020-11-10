const systems = {
  Геодезическая: {
    WGS84: '+proj=longlat +datum=WGS84 +no_defs',
    СК42:
      '+proj=longlat +ellps=krass +towgs84=23.92,-141.27,-80.9,-0,0.35,0.82,-0.12 +no_defs',
  },

  Прямоугольная: {
    СК42: {
      5: '+proj=tmerc +lat_0=0 +lon_0=27 +k=1 +x_0=5500000 +y_0=0 +ellps=krass +towgs84=23.92,-141.27,-80.9,-0,0.35,0.82,-0.12 +units=m +no_defs',
      6: '+proj=tmerc +lat_0=0 +lon_0=33 +k=1 +x_0=6500000 +y_0=0 +ellps=krass +towgs84=23.92,-141.27,-80.9,-0,0.35,0.82,-0.12 +units=m +no_defs")',
      7: '+proj=tmerc +lat_0=0 +lon_0=39 +k=1 +x_0=7500000 +y_0=0 +ellps=krass +towgs84=23.92,-141.27,-80.9,-0,0.35,0.82,-0.12 +units=m +no_defs")',
      8: '+proj=tmerc +lat_0=0 +lon_0=45 +k=1 +x_0=8500000 +y_0=0 +ellps=krass +towgs84=23.92,-141.27,-80.9,-0,0.35,0.82,-0.12 +units=m +no_defs")',
      9: '+proj=tmerc +lat_0=0 +lon_0=51 +k=1 +x_0=9500000 +y_0=0 +ellps=krass +towgs84=23.92,-141.27,-80.9,-0,0.35,0.82,-0.12 +units=m +no_defs")',
      10: '+proj=tmerc +lat_0=0 +lon_0=57 +k=1 +x_0=10500000 +y_0=0 +ellps=krass +towgs84=23.92,-141.27,-80.9,-0,0.35,0.82,-0.12 +units=m +no_defs")',
      11: '+proj=tmerc +lat_0=0 +lon_0=63 +k=1 +x_0=11500000 +y_0=0 +ellps=krass +towgs84=23.92,-141.27,-80.9,-0,0.35,0.82,-0.12 +units=m +no_defs")',
      12: '+proj=tmerc +lat_0=0 +lon_0=69 +k=1 +x_0=12500000 +y_0=0 +ellps=krass +towgs84=23.92,-141.27,-80.9,-0,0.35,0.82,-0.12 +units=m +no_defs',
      13: '+proj=tmerc +lat_0=0 +lon_0=75 +k=1 +x_0=13500000 +y_0=0 +ellps=krass +towgs84=23.92,-141.27,-80.9,-0,0.35,0.82,-0.12 +units=m +no_defs")',
      14: '+proj=tmerc +lat_0=0 +lon_0=81 +k=1 +x_0=14500000 +y_0=0 +ellps=krass +towgs84=23.92,-141.27,-80.9,-0,0.35,0.82,-0.12 +units=m +no_defs")',
      15: '+proj=tmerc +lat_0=0 +lon_0=87 +k=1 +x_0=15500000 +y_0=0 +ellps=krass +towgs84=23.92,-141.27,-80.9,-0,0.35,0.82,-0.12 +units=m +no_defs")',
      16: '+proj=tmerc +lat_0=0 +lon_0=93 +k=1 +x_0=16500000 +y_0=0 +ellps=krass +towgs84=23.92,-141.27,-80.9,-0,0.35,0.82,-0.12 +units=m +no_defs")',
      17: '+proj=tmerc +lat_0=0 +lon_0=99 +k=1 +x_0=17500000 +y_0=0 +ellps=krass +towgs84=23.92,-141.27,-80.9,-0,0.35,0.82,-0.12 +units=m +no_defs")',
      18: '+proj=tmerc +lat_0=0 +lon_0=105 +k=1 +x_0=18500000 +y_0=0 +ellps=krass +towgs84=23.92,-141.27,-80.9,-0,0.35,0.82,-0.12 +units=m +no_defs")',
    },
    WGS84: {
      '33N': '+proj=utm +zone=33 +datum=WGS84 +units=m +no_defs',
      '34N': '+proj=utm +zone=34 +datum=WGS84 +units=m +no_defs',
      '35N': '+proj=utm +zone=35 +datum=WGS84 +units=m +no_defs',
      '36N': '+proj=utm +zone=36 +datum=WGS84 +units=m +no_defs',
      '37N': '+proj=utm +zone=37 +datum=WGS84 +units=m +no_defs',
      '38N': '+proj=utm +zone=38 +datum=WGS84 +units=m +no_defs',
      '39N': '+proj=utm +zone=39 +datum=WGS84 +units=m +no_defs',
      '40N': '+proj=utm +zone=40 +datum=WGS84 +units=m +no_defs',
      '41N': '+proj=utm +zone=41 +datum=WGS84 +units=m +no_defs',
      '42N': '+proj=utm +zone=42 +datum=WGS84 +units=m +no_defs',
      '43N': '+proj=utm +zone=43 +datum=WGS84 +units=m +no_defs',
      '44N': '+proj=utm +zone=44 +datum=WGS84 +units=m +no_defs',
      '45N': '+proj=utm +zone=45 +datum=WGS84 +units=m +no_defs',
    },
  },
};

export default systems;
