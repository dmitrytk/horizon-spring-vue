const fields = [
  {
    label: 'Id',
    key: 'id',
  },
  {
    label: 'Месторождение',
    key: 'name',
    required: true,
    sortable: true,
  },
  {
    label: 'Тип',
    key: 'type',
  },
  {
    label: 'Расположение',
    key: 'location',
  },
];

const wells = [
  {
    label: 'Id',
    key: 'id',
  },
  {
    label: 'Скважина',
    key: 'name',
    databaseColumn: 'well',
    required: true,
    sortable: true,

  },
  {
    label: 'Куст',
    key: 'pad',
    sortable: true,

  },
  {
    label: 'Тип',
    key: 'type',
    sortable: true,

  },
  {
    label: 'Состояние',
    key: 'status',
    sortable: true,

  },
  {
    label: 'Забой',
    key: 'bottom',
    sortable: true,

  },
  {
    label: 'Альтитуда',
    key: 'alt',
    sortable: true,

  },
  {
    label: 'X',
    key: 'x',
  },
  {
    label: 'Y',
    key: 'y',
  },
  {
    label: 'Широта',
    key: 'lat',
  },
  {
    label: 'Долгота',
    key: 'lng',
  },
];

const inclinometry = [
  // {
  //   label: 'Id',
  //   key: 'id',
  // },
  {
    label: 'Скважина',
    key: 'well',
  },
  {
    label: 'Глубина',
    key: 'md',
    required: true,
  },
  {
    label: 'Угол',
    key: 'inc',
  },
  {
    label: 'Азимут',
    key: 'azi',
  },
];

const tables = {
  fields,
  wells,
  inclinometry,
};

export default tables;
