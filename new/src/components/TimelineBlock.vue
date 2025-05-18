<template>
    <div
      class="timeline-block"
      :style="{
        width: `${currentWidth}px`,
        backgroundColor: isSelected ? '#4CAF50' : '#e0e0e0',
        display: currentWidth <= 0 ? 'none' : 'block'
      }"
      @click="$emit('select', index)"
    >
      <div class="timeline-label">
        {{ formattedTime }}
      </div>
    </div>
  </template>

<script setup>
import { computed, defineProps } from 'vue'

const props = defineProps({
  index: Number,
  startTime: Number,
  endTime: Number,
  currentWidth: Number,
  isSelected: Boolean
})

const formattedTime = computed(() => {
  const startHour = Math.floor(props.startTime)
  const startMinute = Math.round((props.startTime % 1) * 60)
  const endHour = Math.floor(props.endTime)
  const endMinute = Math.round((props.endTime % 1) * 60)

  return `${startHour}:${startMinute < 10 ? '0' + startMinute : startMinute}-${endHour}:${endMinute < 10 ? '0' + endMinute : endMinute}`
})
</script>

  <style scoped>
  .timeline-block {
    height: 90px;
    background-color: #e0e0e0;
    border-right: 1px solid #999;
    box-sizing: border-box;
    transition: width 0.3s ease;
    position: relative;
    cursor: pointer;
    flex-shrink: 0;
  }

  .timeline-block:hover {
    background-color: #d0d0d0;
  }

  .timeline-label {
    position: absolute;
    top: 50%;
    left: 50%;
    transform: translate(-50%, -50%);
    font-size: 12px;
    text-align: center;
    pointer-events: none;
  }
  </style>
