import React from "react";
import Cellphone from "../../../../assets/cellphone.png";
import IphoneDownLoad from "../../../../assets/downloadIphone.png";
import AndroidDownLoad from "../../../../assets/downloadAndroid.png";
import "./phoneBook.scss";

export default function PhoneBook() {
  return (
    <section className="section06" id="section06">
      <div className="divBox">
        <div className="text">
          <h2>Sua agenda disponível no seu celular</h2>
          <p>Acesse aonde estiver, em qualquer dispositivo.</p>
          <div className="imgsIcons">
            <img
              className="btnIconIphone"
              src={IphoneDownLoad}
              alt="Donwload App"
            />
            <img
              className="btnIconAndroid"
              src={AndroidDownLoad}
              alt="Donwload App"
            />
          </div>
        </div>
        <div className="img">
          <img className="cellphone" src={Cellphone} alt="cellphone" />
        </div>
      </div>
    </section>
  );
}
