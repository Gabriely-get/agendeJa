import React, { useState } from "react";
import "./newsLetter.scss";

export default function NewsLetter() {
  const [email, setEmail] = useState("");
  return (
    <section className="section07">
      <div className="divBox">
        <h2>Quer saber mais?</h2>
        <p>
          Receba nosso material com as dicas para obter melhores resultados com
          agendamento online
        </p>

        <div>
          <input
            type="email"
            onChange={(event) => {
              setEmail(event.target.value);
            }}
          />
          <button className="primaryBtn">Confirmar</button>
        </div>
      </div>
    </section>
  );
}
