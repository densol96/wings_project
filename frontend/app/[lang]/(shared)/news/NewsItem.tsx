import React from "react";
import { ShortNewsItem } from "@/@types/shared";
import { formatDate } from "@/utils";

const NewsItem = function ({ item }: { item: ShortNewsItem }) {
  return (
    <div className="flex flex-col shadow-md rounded-xl overflow-hidden hover:shadow-lg hover:-translate-y-1 transition-all duration-300 max-w-sm">
      <div className="h-auto overflow-hidden">
        <div className="h-44 overflow-hidden relative">
          <img src={item.imageUrl || `https://www.allianceplast.com/wp-content/uploads/no-image.png`} alt="Attēls" />
        </div>
      </div>
      <div className="bg-white py-4 px-3">
        <h3 className="text-xs mb-2 font-medium">{item.title}</h3>
        <div>
          <p className="text-xs text-gray-400">
            {formatDate(item.startDate)} - {formatDate(item.endDate)}
          </p>
        </div>
        <div>
          <p className="text-xs text-gray-400 break-words">{item.description}...</p>
        </div>
      </div>
    </div>
  );
};

export default NewsItem;
