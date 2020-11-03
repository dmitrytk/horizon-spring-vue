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
    label: 'id',
    key: 'id',
  },
  {
    label: 'Скважина',
    key: 'well',
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

const mer = [
  {
    label: 'Скважина',
    key: 'well',
  },
  {
    label: 'Дата',
    key: 'date',
    required: true,
  },
  {
    label: 'Состояние',
    key: 'status',
  },
  {
    label: 'Дебет',
    key: 'rate',
  },
  {
    label: 'Добыча',
    key: 'production',
  },
  {
    label: 'Суток работы',
    key: 'work_days',
  },
];

const rates = [
  {
    label: 'Скважина',
    key: 'well',
  },
  {
    label: 'Дата',
    key: 'date',
    required: true,
  },
  {
    label: 'Добыча',
    key: 'rate',
  },
  {
    label: 'Динамика',
    key: 'dynamic',
  },
  {
    label: 'Статика',
    key: 'static',
  },
  {
    label: 'Давление',
    key: 'pressure',
  },
];

const zones = [
  {
    label: 'Скважина',
    key: 'well',
  },
  {
    label: 'Название',
    key: 'name',
    required: true,
  },
  {
    label: 'Кровля',
    key: 'top_md',
  },
  {
    label: 'Подошва',
    key: 'bot_md',
  },
  {
    label: 'Кровля АО',
    key: 'top_tvd',
  },
  {
    label: 'Подошва АО',
    key: 'bot_tvd',
  },
  {
    label: 'Мощность',
    key: 'h',
  },
];

const tables = {
  fields,
  wells,
  inclinometry,
  mer,
  rates,
  zones,
};

export default tables;
