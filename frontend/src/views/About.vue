<template>
  <div class="about">
    <h1>This is an about page</h1>
    <input v-model="content" type="text">
    <button @click="getContent">Load</button>
  </div>
</template>

<script>
import axios from 'axios';

export default {
  name: 'About',
  data() {
    return {
      content: '',
    };
  },
  methods: {
    getContent() {
      axios.get('/api')
        .then((result) => {
          this.content = result.data;
        });
    },
  },
};

</script>
