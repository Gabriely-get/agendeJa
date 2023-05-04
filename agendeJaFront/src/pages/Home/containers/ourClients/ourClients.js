import React from "react";
import Google from "../../../../assets/google.png";
import MaryRocha from "../../../../assets/maryRocha.png";
import Meta from "../../../../assets/meta.png";
import Barbershop from "../../../../assets/barbershop.png";
import "./ourClients.scss";

export default function OurClients() {
  return (
    <section className="section05" id="section05">
      <h2>Conheça nossos clientes</h2>
      <div className="divBox">
        <div className="divSon">
          <div className="text">
            <img className="google" src={Google} alt="Google" />
          </div>
          <div className="text">
            <img className="maryRocha" src={MaryRocha} alt="MaryRocha" />
          </div>
          <div className="text">
            <img className="meta" src={Meta} alt="Meta" />
          </div>
          <div className="text">
            <img className="barbershop" src={Barbershop} alt="Barbershop" />
          </div>
        </div>
      </div>
    </section>
  );
}
