<template>
    <div class="cell" :class="{ revealed: cell.revealed, flagged: cell.flagged, mine: cell.mine }" @click="handleClick"
        @contextmenu.prevent="handleRightClick">
        <div v-if="cell.revealed && cell.adjacentMines > 0">{{ cell.adjacentMines }}</div>
        <div v-if="cell.revealed && cell.mine">💥</div>
        <div v-if="cell.flagged">🚩</div>
    </div>
</template>

<script>
export default {
  props: {
    cell: {
      type: Object,
      required: true
    }
  },
  methods: {
    handleClick () {
      this.$emit('click')
    },
    handleRightClick () {
      this.$emit('right-click')
    }
  }
}
</script>

<style scoped>
.cell {
    width: 30px;
    height: 30px;
    display: flex;
    justify-content: center;
    align-items: center;
    background-color: #ddd;
    border: 1px solid #ccc;
    cursor: pointer;
}

.cell.revealed {
    background-color: #fff;
    border: 1px solid #aaa;
}

.cell.flagged {
    background-color: #ffcccc;
}

.cell.mine {
    color: red;
}
</style>
