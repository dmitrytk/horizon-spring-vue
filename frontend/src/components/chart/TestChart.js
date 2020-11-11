import { Bar, Line } from 'vue-chartjs';

export default {
  mixins: [Bar, Line],
  props: {
    data: Object,
  },
  data() {
    return {
      chartdata: {
        labels: this.data.dates,
        datasets: [
          {
            type: 'bar',
            label: 'Дебит, м3/сут',
            backgroundColor: '#7bbbe7',
            data: this.data.rates,
            yAxisID: 'left-axis',
            order: 2,
          },
          {
            type: 'line',
            label: 'Динамика, м',
            backgroundColor: '#ff2222',
            data: this.data.dynamic,
            yAxisID: 'right-axis',
            order: 1,
          },
          {
            type: 'line',
            label: 'Статика, м',
            backgroundColor: '#52ff00',
            data: this.data.static,
            yAxisID: 'right-axis',
            order: 1,

          },
        ],
      },
      options: {
        maxTicksLimit: 15,
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

        // Axes options
        scales: {
          yAxes: [{
            type: 'linear', // only linear but allow scale type registration. This allows extensions to exist solely for log scale for instance
            labelString: 'Дебит',
            display: true,
            position: 'left',
            id: 'left-axis',
          }, {
            type: 'linear',
            display: true,
            position: 'right',
            id: 'right-axis',

            // grid line settings
            gridLines: {
              drawOnChartArea: false, // only want the grid lines for one axis to show up
            },
          }],
        }, // animation duration after a resize
      },
    };
  },

  mounted() {
    this.renderChart(this.chartdata, this.options);
  }
  ,
};
