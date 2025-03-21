import { useState } from "react";
import styles from './App.module.css';
import clsx from "clsx";

const animalEmojis = ['🐶', '🐱', '🐭'];
const fruitEmojis = ['🍎', '🍌', '🍇'];

function getInitialCards(theme: 'animals' | 'fruits') {
  const emojis = theme === 'animals' ? animalEmojis : fruitEmojis;
  return emojis.concat(emojis).sort(() => Math.random() > 0.5 ? -1 : 1);
}

export default function App() {
  const [theme, setTheme] = useState<'animals' | 'fruits'>('animals');
  const [cards, setCards] = useState(getInitialCards('animals'));
  const [flippedIndexes, setFlippedIndexes] = useState<number[]>([]);

  function handleClick(index: number) {
    if (flippedIndexes.includes(index)) return;
    if (flippedIndexes.length === 2) return;
    setFlippedIndexes([...flippedIndexes, index]);
  }

  return (
    <>
      {/* ToggleGroup */}
      <div className={styles.toggleGroup}>
        <button
          onClick={() => {
            setTheme('animals');
            setCards(getInitialCards('animals'));
            setFlippedIndexes([]);
          }}
          className={theme === 'animals' ? styles.activeToggle : ''}
        >
          🐶 Animals
        </button>
        <button
          onClick={() => {
            setTheme('fruits');
            setCards(getInitialCards('fruits'));
            setFlippedIndexes([]);
          }}
          className={theme === 'fruits' ? styles.activeToggle : ''}
        >
          🍎 Fruits
        </button>
      </div>

      {/* Spielbereich */}
      <div className={styles.container}>
        {cards.map((card, index) => {
          const isFlipped = flippedIndexes.includes(index);
          return (
            <button
              key={index}
              className={clsx(styles.card, isFlipped && styles.flipped)}
              onClick={() => handleClick(index)}
              tabIndex={isFlipped ? -1 : 0}
            >
              {isFlipped ? card : "❓"}
            </button>
          );
        })}
      </div>
    </>
  );
}