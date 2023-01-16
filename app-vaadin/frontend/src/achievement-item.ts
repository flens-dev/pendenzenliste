import {css, customElement, html, LitElement, property} from "lit-element";
import "@vaadin/vaadin-icon"

type achievementState = "LOCKED" | "UNLOCKED";

const styles = css`
  .achievement {
    display: flex;
    flex-direction: column;
    align-items: center;
    justify-items: center;
    grid-gap: 5px
  }

  .achievement vaadin-icon {
    font-size: 60px;
  }

  .achievement.UNLOCKED vaadin-icon {
    color: var(--lumo-success-color);
  }

  .achievement.LOCKED vaadin-icon {
    color: gold;
  }

  .headline {
    font-weight: 600;
    font-size: 1.5rem;
    text-align: center;
  }

  .description {
    font-weight: 200;
  }
`

@customElement("achievement-item")
export default class AchievementItem extends LitElement {

    @property({type: String})
    state: achievementState = "LOCKED"

    @property({type: String})
    headline: String = "";

    @property({type: String})
    description: String = "";

    render() {
        return html`
            <div class="achievement ${this.state}">
                <vaadin-icon icon="${this._determineIcon()}"></vaadin-icon>
                <span class="headline">${this.headline}</span>
                <span class="description">${this.description}</span>
            </div>
        `
    }

    static get styles() {
        return [styles]
    }

    private _determineIcon() {
        return this.state === "UNLOCKED" ? "vaadin:check-circle" : "vaadin:question-circle"
    }
}

declare global {
    interface HTMLElementTagNameMap {
        "achievement-item": AchievementItem;
    }
}