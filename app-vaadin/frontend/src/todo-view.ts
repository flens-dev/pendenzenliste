import {css, customElement, html, LitElement} from "lit-element";

const styles = css`
  :host {
    display: block;
    height: 100%
  }

  .view {
    height: 100%;
    display: grid;
    grid-template-columns: 2fr 1fr;
  }

  .list {
    padding: var(--lumo-space-m);
  }

  .editor {
    height: 100%;
  }
`

@customElement("todo-view")
export default class TodoView extends LitElement {
    static get styles() {
        return [styles]
    }

    render() {
        return html`
            <div class="view">
                <div class="list">
                    <slot name="list"></slot>
                </div>
                <div class="editor">
                    <slot name="editor"></slot>
                </div>
            </div>
        `
    }
}

declare global {
    interface HTMLElementTagNameMap {
        "todo-view": TodoView;
    }
}