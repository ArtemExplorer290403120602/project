function previewPhoto(event) {
    const file = event.target.files[0];
    const reader = new FileReader();
    reader.onload = function(e) {
        const img = document.getElementById('preview');
        img.src = e.target.result;
        img.style.display = 'block';
        localStorage.setItem('photo', e.target.result);
    }
    reader.readAsDataURL(file);
}