window.onload = function() {
    const opt = document.getElementById("stockOptions");
    for (let i = 1; i <= 6; i++) {
       let opt1 = document.createElement('OPTION');
       opt1.value = i;
       opt1.innerHTML = i;
       opt.appendChild(opt1);
    }
};