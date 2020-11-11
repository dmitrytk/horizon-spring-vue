<template>
  <b-tab title="Карта">
    <b-card-text>
      <h1>Здесь будет карта</h1>
      <yandex-map :coords="coords" :settings="settings">
        <ymap-marker
          :coords="coords"
          :icon="markerIcon"
          marker-id="123"
        />
      </yandex-map>
    </b-card-text>
  </b-tab>
</template>

<script>
import { yandexMap, ymapMarker } from 'vue-yandex-maps';

const settings = {
  apiKey: process.env.VUE_APP_MAP_API_KEY,
  lang: 'ru_RU',
  coordorder: 'latlong',
};

export default {
  name: 'MapTab',
  components: { yandexMap, ymapMarker },
  data() {
    return {
      settings,
      coords: [54, 39],
      markerIcon: {
        layout: 'default#imageWithContent',
        imageHref: 'https://image.flaticon.com/icons/png/512/33/33447.png',
        imageSize: [43, 43],
        imageOffset: [0, 0],
        content: '123 v12',
        contentOffset: [0, 15],
        contentLayout: '<div style="background: red; width: 50px; color: #FFFFFF; font-weight: bold;">$[properties.iconContent]</div>',
      },
    };
  },
};
</script>

<style scoped>

</style>
