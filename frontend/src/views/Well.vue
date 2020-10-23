<template>
  <div v-if="wellLoaded" class="container">
    <h1 v-once class="text-center my-3">Скважина {{ well.name }}</h1>
    <div>
      <b-card no-body>
        <b-tabs card>
          <b-tab active title="Данные">
            <b-card-text>
              <p>Well</p>
            </b-card-text>
          </b-tab>
        </b-tabs>
      </b-card>
    </div>
  </div>
</template>

<script>
import {AXIOS} from "@/config/http-commons";

export default {
  name: "Well",
  data() {
    return {
      wellLoaded: false,
      well: {},
    };
  },
  created() {
    this.fetchWell();
  },
  methods: {
    fetchWell() {
      AXIOS.get(`/wells/${this.$route.params.id}`)
        .then(res => {
          this.well = res.data;
          this.wellLoaded = true;
        })
    },

  },
}
</script>

<style scoped>

</style>
