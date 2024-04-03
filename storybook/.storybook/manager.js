import { addons } from '@storybook/addons';
import {create} from "@storybook/theming";
import logo from "../assets/logo.png";

addons.setConfig({
    theme: create({
        base: 'light',
        brandTitle: 'Connect IM',
        brandUrl: "https://komune-io.github.io/connect-im/",
        brandImage: logo,
    }),
    showToolbar: false
});
