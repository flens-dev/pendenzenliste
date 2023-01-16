import {css, customElement, html, LitElement} from "lit-element";
import "@vaadin/tabsheet";
import "@vaadin/vaadin-tabs";
import "@vaadin/vaadin-icon";

const styles = css`
  :host {
    display: block;
    height: 100%
  }

  .view,
  .view vaadin-tabsheet {
    height: 100%;
  }

  .tab-content {
    height: 100%;
    width: 100%;
  }

  .todos {
    height: 100%;
  }

  .todo-layout {
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

  .achievements {
    display: grid;
    grid-template-columns: repeat(4, 1fr);
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
                <vaadin-tabsheet>
                    <vaadin-tabs slot="tabs">
                        <vaadin-tab id="todos" theme="icon-on-top">
                            <vaadin-icon icon="vaadin:records"></vaadin-icon>
                            ToDos
                        </vaadin-tab>
                        <vaadin-tab id="achievements" theme="icon-on-top">
                            <vaadin-icon icon="vaadin:flag"></vaadin-icon>
                            Achievements
                        </vaadin-tab>
                    </vaadin-tabs>

                    <div tab="todos" class="todos tab-content">
                        <div class="todo-layout">
                            <div class="list">
                                <slot name="list"></slot>
                            </div>
                            <div class="editor">
                                <slot name="editor"></slot>
                            </div>
                        </div>
                    </div>

                    <div tab="achievements" class="achievements tab-content">
                        <slot name="achievements"></slot>
                    </div>
                </vaadin-tabsheet>
            </div>
        `
    }
}

declare global {
    interface HTMLElementTagNameMap {
        "todo-view": TodoView;
    }
}