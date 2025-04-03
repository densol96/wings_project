import React from "react";
import { getDictionary } from "@/dictionaries/dictionaries";
import { fetcher, formatDate } from "@/utils";

import { PagePropsWithId, SingleNewsItem } from "@/types";
import { Gallery, Heading, MyImage } from "@/components";

export const revalidate = 0;

type NewsItemDictionary = {
  startDate: string;
  endDate: string;
  postedOn: string;
  location: string;
};

// export const generateMetadata = async ({ params: { lang } }: PageProps) => {
//   const dict: NewsDictionaryType = (await getDictionary(lang)).news;
//   return {
//     title: dict.title,
//   };
// };

export default async function Page({ params: { id, lang } }: PagePropsWithId) {
  const dict: NewsItemDictionary = (await getDictionary(lang)).newsItem;
  const event = await fetcher<SingleNewsItem>(`${process.env.NEXT_PUBLIC_BACKEND_URL_EXTENDED}/events/${id}?lang=${lang}`);
  return (
    <div className="md:block flex md:flex-row flex-col">
      <div className="min-h-[400px] relative overflow-hidden rounded-md shadow-lg lg:w-[50%] lg:float-right lg:ml-16 my-10 w-full">
        {event.imageDtos.length <= 0 ? <MyImage lang={lang} /> : <Gallery images={event.imageDtos} />}
        {event.imageDtos.length > 1 && (
          <span className="pointer-events-none absolute top-1/2 left-1/2 -translate-x-1/2 -translate-y-1/2 p-6 bg-slate-100 rounded-lg opacity-60 font-bold text-xl group-hover:opacity-90 transition-opacity">
            {`1/${event.imageDtos.length}`}
          </span>
        )}
      </div>
      <article className="flex-col items-start gap-2">
        <div className="lg:block sm:flex flex-row block justify-between items-center">
          <header>
            {event.categoryName && <p className="font-bold uppercase text-primary-bright tracking-wider">{event.category}</p>}
            <Heading size="xl">{event.translationDto.title}</Heading>
          </header>
          <div className="mb-6 flex gap-8 lg:mb-8">
            <div className="flex flex-col gap-2">
              <span className="font-medium">{dict.startDate}:</span>
              <span className="text-gray-500">{formatDate(event.startDate)}</span>
            </div>
            <div className="flex flex-col gap-2">
              <span className="font-medium">{dict.endDate}:</span>
              <span className="text-gray-500">{formatDate(event.endDate)}</span>
            </div>
            <div className="flex flex-col gap-2">
              <span className="font-medium">{dict.postedOn}:</span>
              <span className="text-gray-500">{formatDate(event.createdAt)}</span>
            </div>
            {event.translationDto.location && (
              <div className="flex flex-col gap-2">
                <span className="font-medium">{dict.location}:</span>
                <span className="text-gray-500">{event.translationDto.location}</span>
              </div>
            )}
          </div>
        </div>

        <div className="mb-8 mt-8 h-[0.3px] lg:w-[45%] w-full bg-gray-700 opacity-50"></div>
        <p>
          {event.translationDto.description}
          Lorem ipsum dolor sit, amet consectetur adipisicing elit. Exercitationem quae atque, ut aliquid impedit obcaecati. In dolore impedit omnis. Expedita
          eius cupiditate amet voluptate, nostrum facilis numquam repellendus suscipit corporis. Delectus suscipit rem, dolores est quaerat quo optio nam quia
          quam eum tempora neque assumenda eligendi. Animi ut blanditiis aliquam totam veniam, ipsa reprehenderit deserunt ullam. Nihil saepe voluptatibus qui?
          Enim quam natus odit ratione, ipsa placeat totam accusamus quod blanditiis dignissimos aliquam dolores officiis rerum neque commodi, quaerat dolore
          eligendi asperiores incidunt numquam minus. Distinctio esse quos commodi minima. Alias aut voluptate quidem possimus voluptas, harum accusantium quae
          pariatur, itaque id voluptatibus officiis quasi eius quod et eligendi vitae, autem sequi soluta quas. Quam nulla alias harum ipsam voluptates. Eveniet
          neque, tenetur voluptas accusamus quae laudantium cumque libero minima esse quos iste nihil quis non nemo provident necessitatibus asperiores eum iure
          adipisci doloribus! Cupiditate voluptatum dignissimos maxime enim sed! Enim quae voluptates dicta vitae asperiores totam quos ipsam earum eaque
          tenetur labore voluptatum quidem in repudiandae fugit ad a nisi corrupti unde voluptatibus quibusdam, ex dolore voluptate architecto! Nobis. Sapiente
          illo id animi ipsam voluptatibus distinctio architecto voluptates! Recusandae necessitatibus consequatur porro iste earum ut nam, maiores ullam labore
          veritatis perferendis, assumenda harum perspiciatis cupiditate voluptate esse laboriosam delectus. Animi, voluptates magnam, odit unde at corrupti
          delectus asperiores ipsum vel nisi in a aliquam, sed enim! Consectetur temporibus nihil tempore veritatis eaque dignissimos, magnam, molestias quis
          amet repellendus non? Vitae alias rerum iusto quibusdam dolore accusantium blanditiis, rem velit, ratione sint nesciunt ipsum optio nostrum officiis
          maxime recusandae. Quaerat quas possimus non ducimus perspiciatis quisquam dolores asperiores, officiis voluptatum. Alias itaque magni nihil
          architecto qui cumque minima quod facilis praesentium dolore optio porro accusantium quasi saepe impedit nobis sint veritatis, quam ex voluptatem
          nemo. Delectus suscipit nulla illo consequuntur?
        </p>
      </article>
    </div>
  );
}

// return (
//   <div className="grid gap-12 sm:gap-20 lg:grid-cols-2">
//     <div className="flex flex-col items-start gap-2">
//       {event.category && <p className="font-bold uppercase text-primary-bright tracking-wider">{event.category}</p>}
//       <Heading size="xl" className="text-center">
//         {event.translation.title}
//       </Heading>
//       <div className="mb-6 flex gap-8 lg:mb-8">
//         <div className="flex flex-col gap-2">
//           <span className="font-medium">{dict.startDate}:</span>
//           <span className="text-gray-500">{formatDate(event.startDate)}</span>
//         </div>
//         <div className="flex flex-col gap-2">
//           <span className="font-medium">{dict.endDate}:</span>
//           <span className="text-gray-500">{formatDate(event.endDate)}</span>
//         </div>
//         <div className="flex flex-col gap-2">
//           <span className="font-medium">{dict.postedOn}:</span>
//           <span className="text-gray-500">{formatDate(event.createdAt)}</span>
//         </div>
//         {event.translation.location && (
//           <div className="flex flex-col gap-2">
//             <span className="font-medium">{dict.location}:</span>
//             <span className="text-gray-500">{event.translation.location}</span>
//           </div>
//         )}
//       </div>
//       <p>
//         Lorem ipsum dolor sit, amet consectetur adipisicing elit. Exercitationem quae atque, ut aliquid impedit obcaecati. In dolore impedit omnis. Expedita eius
//         cupiditate amet voluptate, nostrum facilis numquam repellendus suscipit corporis. Delectus suscipit rem, dolores est quaerat quo optio nam quia quam eum tempora
//         neque assumenda eligendi. Animi ut blanditiis aliquam totam veniam, ipsa reprehenderit deserunt ullam. Nihil saepe voluptatibus qui? Enim quam natus odit
//         ratione, ipsa placeat totam accusamus quod blanditiis dignissimos aliquam dolores officiis rerum neque commodi, quaerat dolore eligendi asperiores incidunt
//         numquam minus. Distinctio esse quos commodi minima. Alias aut voluptate quidem possimus voluptas, harum accusantium quae pariatur, itaque id voluptatibus
//         officiis quasi eius quod et eligendi vitae, autem sequi soluta quas. Quam nulla alias harum ipsam voluptates. Eveniet neque, tenetur voluptas accusamus quae
//         laudantium cumque libero minima esse quos iste nihil quis non nemo provident necessitatibus asperiores eum iure adipisci doloribus! Cupiditate voluptatum
//         dignissimos maxime enim sed! Enim quae voluptates dicta vitae asperiores totam quos ipsam earum eaque tenetur labore voluptatum quidem in repudiandae fugit ad a
//         nisi corrupti unde voluptatibus quibusdam, ex dolore voluptate architecto! Nobis. Sapiente illo id animi ipsam voluptatibus distinctio architecto voluptates!
//         Recusandae necessitatibus consequatur porro iste earum ut nam, maiores ullam labore veritatis perferendis, assumenda harum perspiciatis cupiditate voluptate
//         esse laboriosam delectus. Animi, voluptates magnam, odit unde at corrupti delectus asperiores ipsum vel nisi in a aliquam, sed enim! Consectetur temporibus
//         nihil tempore veritatis eaque dignissimos, magnam, molestias quis amet repellendus non? Vitae alias rerum iusto quibusdam dolore accusantium blanditiis, rem
//         velit, ratione sint nesciunt ipsum optio nostrum officiis maxime recusandae. Quaerat quas possimus non ducimus perspiciatis quisquam dolores asperiores,
//         officiis voluptatum. Alias itaque magni nihil architecto qui cumque minima quod facilis praesentium dolore optio porro accusantium quasi saepe impedit nobis
//         sint veritatis, quam ex voluptatem nemo. Delectus suscipit nulla illo consequuntur?
//       </p>
//     </div>
//     <div className="min-h-[400px] relative max-h-[800px] overflow-hidden rounded-md shadow-lg group place-self-start w-full">
//       {event.images.length <= 0 ? <MyImage lang={lang} /> : <Gallery images={event.images} />}
//       {event.images.length > 0 && (
//         <span className="pointer-events-none absolute top-1/2 left-1/2 -translate-x-1/2 -translate-y-1/2 p-6 bg-slate-100 rounded-lg opacity-60 font-bold text-xl group-hover:opacity-90 transition-opacity">
//           {`1/${event.images.length}`}
//         </span>
//       )}
//     </div>
//   </div>
