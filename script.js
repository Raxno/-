// Элементы DOM
const addBtn = document.querySelector('.btn__add-film');
const cardsWrap = document.querySelector('.cards__wrap');
const filterButtons = document.querySelectorAll('.filter-btn');

// Изначальные данные (можно оставить пустым, если карточки уже есть в HTML)
const tracks = [];

// Функция для создания карточки
function createCard(title, year, rating, description = "Описание отсутствует") {
  const card = document.createElement('div');
  card.className = 'card';
  card.innerHTML = `
    <img src="./image/true.webp" alt="" class="card__image">
    <h4 class="card-track__name">${title}</h4>
    <p class="desc">${description}</p>
    <p class="card__rate">Рейтинг: ${rating} ⭐</p>
    <p class="card__date">${year}</p>
    <button class="delete-btn">Удалить</button>
  `;

  // Обработчик для удаления карточки
  card.querySelector('.delete-btn').addEventListener('click', () => {
    card.remove();
  });

  return card;
}

// Добавление нового трека
addBtn.addEventListener('click', () => {
  const title = document.querySelector('.track__name').value.trim();
  const year = document.querySelector('.time').value;
  const rating = document.querySelector('.rate').value;

  if (title && year && rating) {
    const newCard = createCard(title, year, rating);
    cardsWrap.appendChild(newCard);

    // Очистка полей ввода
    document.querySelector('.track__name').value = '';
    document.querySelector('.time').value = '';
    document.querySelector('.rate').value = '';
  } else {
    alert('Заполните все поля!');
  }
});

// Фильтрация треков
filterButtons.forEach(btn => {
    btn.addEventListener('click', () => {
      const filter = btn.dataset.filter;
      const cards = document.querySelectorAll('.card');
  
      cards.forEach(card => {
        const year = parseInt(card.querySelector('.card__date').textContent);
        const ratingText = card.querySelector('.card__rate').textContent; // "Рейтинг: 9 ⭐"
        const rating = parseInt(ratingText.match(/\d+/)[0]); // Извлекаем число из текста
  
        switch (filter) {
          case 'all':
            card.style.display = 'flex';
            break;
          case 'watched': // Старые треки (до 2024)
            card.style.display = year < 2024 ? 'flex' : 'none';
            break;
          case 'unwatched': // Треки с рейтингом == 10
            card.style.display = rating === 10 ? 'flex' : 'none';
            break;
        }
      });
    });
  });