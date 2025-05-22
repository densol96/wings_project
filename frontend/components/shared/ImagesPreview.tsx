type Props = {
  previews: string[];
  handleRemove: (index: number) => void;
};

const ImagesPreview = ({ previews, handleRemove }: Props) => {
  return (
    <div className="flex gap-4 flex-wrap">
      {previews.map((src, index) => (
        <div key={index} className="relative">
          <img src={src} alt={`preview-${index}`} className="w-32 h-32 object-cover rounded border" />
          <button type="button" onClick={() => handleRemove(index)} className="absolute top-1 right-1 bg-red-600 text-white text-xs rounded-full px-2 py-0.5">
            ✕
          </button>
        </div>
      ))}
    </div>
  );
};

export default ImagesPreview;
