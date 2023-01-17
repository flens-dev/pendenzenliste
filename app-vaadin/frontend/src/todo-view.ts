import {css, customElement, html, LitElement} from "lit-element";
import "@vaadin/tabsheet";
import "@vaadin/vaadin-tabs";
import "@vaadin/vaadin-icon";
import "@vaadin/vaadin-app-layout";
import {TabsSelectedChangedEvent} from "@vaadin/tabs";

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
    padding: 50px;
    display: flex;
    grid-template-columns: repeat(4, 1fr);
    grid-gap: 50px;
  }
`

interface ToDoViewServerInterface {
    refreshAchievements(): void;
}


@customElement("todo-view")
export default class TodoView extends LitElement {
    private $server?: ToDoViewServerInterface;

    static get styles() {
        return [styles]
    }

    render() {
        return html`
            <vaadin-app-layout>
                <vaadin-drawer-toggle slot="navbar"></vaadin-drawer-toggle>
                <vaadin-tabs slot="drawer" orientation="vertical"
                             @selected-changed="${this.selectedChanged}">
                    <vaadin-tab value="todos">
                        <vaadin-icon icon="vaadin:records"></vaadin-icon>
                        ToDos
                    </vaadin-tab>
                    <vaadin-tab>
                        <vaadin-icon icon="vaadin:flag"></vaadin-icon>
                        Achievements
                    </vaadin-tab>
                </vaadin-tabs>
                <div class="view">
                    <div id="todos" class="todos tab-content">
                        <div class="todo-layout">
                            <div class="list">
                                <slot name="list"></slot>
                            </div>
                            <div class="editor">
                                <slot name="editor"></slot>
                            </div>
                        </div>
                    </div>

                    <div id="achievements" class="achievements">
                        <slot name="achievements"></slot>
                    </div>
                </div>
            </vaadin-app-layout>
        `
    }

    selectedChanged(e: TabsSelectedChangedEvent) {
        const contentElements = ["achievements", "todos"];

        const that = this;

        contentElements.forEach(element =>
            // @ts-ignore
            that.shadowRoot.getElementById(element).style.display = "none");

        if (e.detail.value === 0) {
            this.shadowRoot!.getElementById("todos")!.style.display = "block";
        } else if (e.detail.value === 1) {
            this.$server!.refreshAchievements();
            this.shadowRoot!.getElementById("achievements")!.style.display = "grid";
        }
        console.log(e.detail.value)
    }
}

declare global {
    interface HTMLElementTagNameMap {
        "todo-view": TodoView;
    }
}