import { Bar } from 'vue-chartjs';

export default {
  mixins: [Bar],
  props: {
    data: Array,
  },
  data() {
    return {
      chartdata: {
        labels: this.data.map((el) => el.date),
        datasets: [
          {
            type: 'bar',
            label: 'Добыча, м3',
            backgroundColor: '#7bbbe7',
            data: this.data.map((el) => el.production),
          },
        ],
      },
      options: {
        title: {
          text: 'Добыча',
          display: true,
          fontSize: 14,
        },
        // Basic options
        responsive: true,
        maintainAspectRatio: false,

        // Performance options
        animation: {
          duration: 0, // general animation time
        },
        hover: {
          animationDuration: 0, // duration of animations when hovering an item
        },
        responsiveAnimationDuration: 0,
        showLines: false, // disable line draw

      },
    };
  },

  mounted() {
    this.renderChart(this.chartdata, this.options);
  }
  ,
};
