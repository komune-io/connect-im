const renderer = `
import React from 'react'
import {mdx} from '/node_modules/@mdx-js/react'
`;

module.exports = ({ config }) => {
  config.module.rules[2].use[1].options = {
    ...config.module.rules[2].use[1].options,
    renderer
  };
  return config;
};
