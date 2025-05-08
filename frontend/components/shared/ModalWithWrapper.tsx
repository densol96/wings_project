import Modal from "./Modal";

type Props = {
  children: React.ReactNode;
};

const ModalWithWrapper = ({ children }: Props) => {
  return (
    <div className="fixed w-full h-full flex items-center justify-center backdrop-blur-sm top-0 left-0">
      <Modal>{children}</Modal>
    </div>
  );
};

export default ModalWithWrapper;
