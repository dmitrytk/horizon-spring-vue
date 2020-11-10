<template>
  <div class="container">
    <div class="page-header">
      <h2 class="text-center my-3">Конвертер координат</h2>
    </div>
    <b-card>
      <b-row>
        <!-- INPUT -->
        <b-col class="mb-3" lg="6">
          <h4 class="text-center text-secondary mb-3">Исходные</h4>
          <b-row class="mb-3">
            <b-col>
              <b-form-group
                label="Тип координаты"
              >
                <b-form-select v-model="selectedInType" :options="syst"></b-form-select>

              </b-form-group>
              <div class="form-group">
                <label>Система координат</label>
                <select v-model="selectedInSystem" class="form-control">
                  <option
                    v-for="(value, system, index) in inSystem"
                    :key="index"
                  >{{ system }}
                  </option
                  >
                </select>
              </div>
            </b-col>
            <b-col>
              <div class="form-group">
                <label>Зона</label>
                <select
                  v-model="selectedInZone"
                  :disabled="selectedInType === 'Геодезическая'"
                  class="form-control"
                >
                  <option
                    v-for="(value, zone, index) in inZone"
                    :key="index"
                  >{{ zone }}
                  </option
                  >
                </select>
              </div>
            </b-col>
          </b-row>
          <textarea v-model="inData" class="form-control" rows="10"/>
        </b-col>

        <!-- OUTPUT -->
        <b-col class="mb-3" lg="6">
          <h4 class="text-center text-secondary mb-3">Результат</h4>
          <b-row class="mb-3">
            <b-col>
              <b-form-group
                label="Тип координаты"
              >
                <b-form-select v-model="selectedOutType" :options="syst"></b-form-select>
              </b-form-group>
              <div class="form-group">
                <label>Система координат</label>
                <select v-model="selectedOutSystem" class="form-control">
                  <option
                    v-for="(value, system, index) in outSystem"
                    :key="index"
                  >{{ system }}
                  </option
                  >
                </select>
              </div>
            </b-col>
            <b-col>
              <div class="form-group">
                <label>Зона</label>
                <select
                  v-model="selectedOutZone"
                  :disabled="selectedOutType === 'Геодезическая'"
                  class="form-control"
                >
                  <option
                    v-for="(value, zone, index) in outZone"
                    :key="index"
                  >{{ zone }}
                  </option
                  >
                </select>
              </div>
              <div class="form-group">
                <label>Формат градусов</label>
                <div id="degreeType">
                  <input
                    v-model="degreeType"
                    type="radio"
                    value="ГГ MM СС.с"
                  />
                  ГГ MM СС.с
                  <input
                    v-model="degreeType"
                    class="ml-3"
                    type="radio"
                    value="ГГ.гггггг"
                  />
                  ГГ.гггггг
                </div>
              </div>
            </b-col>
          </b-row>
          <textarea v-model="outData" class="form-control" rows="10"/>
        </b-col>
      </b-row>

      <!-- BUTTONS -->
      <div class="text-left">
        <button class="btn btn-primary" @click="convert">
          Ковертировать
          <v-icon name="angle-double-right"/>
        </button>
        <button
          v-clipboard:copy="outData"
          v-clipboard:success="onCopy"
          class="btn btn-info ml-2"
        >
          Копировать
          <v-icon name="copy"/>
        </button>
        <button class="btn btn-danger ml-2" @click="clear">
          Очистить
          <v-icon name="trash"/>
        </button>
      </div>
    </b-card>
  </div>
</template>

<script>
import { coordinates } from '@/data/mock';
import systems from '../util/coordinates/systems';
import convert from '../util/coordinates/convert';

export default {
  data() {
    return {
      syst: Object.keys(systems),
      conv: convert,
      selectedInType: 'Геодезическая',
      selectedInSystem: 'СК42',
      selectedInZone: '',
      selectedOutType: 'Геодезическая',
      selectedOutSystem: 'СК42',
      selectedOutZone: '',
      inData: coordinates,
      outData: '',
      degreeType: 'ГГ MM СС.с',
    };
  },

  computed: {
    inSystem() {
      return systems[this.selectedInType];
    },
    inZone() {
      return this.selectedInType !== 'Геодезическая'
        ? systems[this.selectedInType][this.selectedInSystem]
        : null;
    },
    outSystem() {
      return systems[this.selectedOutType];
    },
    outZone() {
      return this.selectedOutType !== 'Геодезическая'
        ? systems[this.selectedOutType][this.selectedOutSystem]
        : null;
    },
  },

  methods: {
    convert() {
      try {
        const result = convert(
          this.selectedInType,
          this.selectedInSystem,
          this.selectedInZone,
          this.inData,
          this.selectedOutType,
          this.selectedOutSystem,
          this.selectedOutZone,
          this.degreeType,
          this.outData,
        );
        this.outData = result;
      } catch (e) {
        console.log(e);
      }
    },
    onCopy() {
      this.$toasted.show('Скопировано в буфер', {
        duration: 3000,
        position: 'top-center',
      });
    },
    clear() {
      this.inData = '';
      this.outData = '';
    },
  },
};
</script>

<style lang="scss">
#degreeType {
  padding: 6px;
}
</style>
