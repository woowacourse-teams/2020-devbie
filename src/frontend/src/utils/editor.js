import VueMarkdownEditor from "@kangc/v-md-editor";
import "@kangc/v-md-editor/lib/style/base-editor.css";
import githubTheme from "@kangc/v-md-editor/lib/theme/github.js";
import enUS from "@kangc/v-md-editor/lib/lang/en-US";
import createCopyCodePlugin from "@kangc/v-md-editor/lib/plugins/copy-code/index";
import Vue from "vue";

VueMarkdownEditor.lang.use("en-US", enUS);
VueMarkdownEditor.use(createCopyCodePlugin());
VueMarkdownEditor.use(githubTheme);
Vue.use(VueMarkdownEditor);

export const editor = new Vue();
