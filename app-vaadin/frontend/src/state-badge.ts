import {customElement, html, LitElement, property} from "lit-element";
import {badge} from "@vaadin/vaadin-lumo-styles";

type todoState = "DONE" | "OPEN";

@customElement("state-badge")
export default class StateBadge extends LitElement {

    @property({type: String})
    state: todoState = "OPEN"

    render() {
        return html`
            <span theme="badge ${this.determineColorClass()}">
                ${this.state}
            </span>
        `
    }

    private determineColorClass(): string {
        return this.state === "DONE" ? "success" : "";
    }

    static get styles() {
        return [badge]
    }
}

declare global {
    interface HTMLElementTagNameMap {
        "state-badge": StateBadge;
    }
}