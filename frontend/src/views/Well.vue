<template>
  <div v-if="wellLoaded" class="container">
    <h2 v-once class="text-center my-3">Скважина {{ well.name }}</h2>
    <b-breadcrumb :items="bread"></b-breadcrumb>
    <div>
      <b-card no-body>
        <b-tabs card>
          <b-tab active title="Данные">
            <b-card-text>
              <WellForm v-bind:well="well" @sendWell="update"/>
            </b-card-text>
          </b-tab>
        </b-tabs>
      </b-card>
    </div>
  </div>
</template>

<script>
import WellForm from '@/components/form/WellForm.vue';
import WellService from '@/services/WellService';

export default {
  name: 'Well',
  components: { WellForm },
  data() {
    return {
      wellLoaded: false,
      well: {},
      bread: [
        { text: 'Месторождения', to: '/fields' },
        { text: 'Месторождение', to: '/fields/1' },
        { text: '', active: true },
      ],
    };
  },
  created() {
    this.fetchWell();
  },
  methods: {
    fetchWell() {
      WellService.getById(this.$route.params.id)
        .then((res) => {
          this.well = res.data;
          this.wellLoaded = true;
        });
    },
    update(data) {
      WellService.update(this.$route.params.id, data)
        .then(() => {
          this.$toasted.show('Данные сохранены', {
            position: 'top-center',
            duration: 3000,
          });
        });
    },
    sendWell() {
      this.$emit('sendWell', this.well);
    },

  },
};
</script>

<style scoped>

</style>
