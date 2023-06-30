<?php

namespace App\Controller;

use App\Entity\Items;
use App\Form\ItemsType;
use App\Manager\CartManager;
use App\Repository\ItemsRepository;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;
use App\Form\AddToCartType;

#[Route('/items')]
class ItemsController extends AbstractController
{
    #[Route('/', name: 'app_items_index', methods: ['GET'])]
    public function index(ItemsRepository $itemsRepository): Response
    {
        return $this->render('items/index.html.twig', [
            'items' => $itemsRepository->findAll(),
        ]);
    }

    #[Route('/new', name: 'app_items_new', methods: ['GET', 'POST'])]
    public function new(Request $request, ItemsRepository $itemsRepository): Response
    {
        $item = new Items();
        $form = $this->createForm(ItemsType::class, $item);
        $form->handleRequest($request);

        if ($form->isSubmitted() && $form->isValid()) {
            $itemsRepository->add($item);
            return $this->redirectToRoute('app_items_index', [], Response::HTTP_SEE_OTHER);
        }

        return $this->renderForm('items/new.html.twig', [
            'item' => $item,
            'form' => $form,
        ]);
    }

    #[Route('/{id}', name: 'app_items_show', methods: ['GET'])]
    public function show(Items $item): Response
    {
        return $this->render('items/show.html.twig', [
            'item' => $item,
        ]);
    }

    #[Route('/{id}/edit', name: 'app_items_edit', methods: ['GET', 'POST'])]
    public function edit(Request $request, Items $item, ItemsRepository $itemsRepository): Response
    {
        $form = $this->createForm(ItemsType::class, $item);
        $form->handleRequest($request);

        if ($form->isSubmitted() && $form->isValid()) {
            $itemsRepository->add($item);
            return $this->redirectToRoute('app_items_index', [], Response::HTTP_SEE_OTHER);
        }

        return $this->renderForm('items/edit.html.twig', [
            'item' => $item,
            'form' => $form,
        ]);
    }

    #[Route('/{id}', name: 'app_items_delete', methods: ['POST'])]
    public function delete(Request $request, Items $item, ItemsRepository $itemsRepository): Response
    {
        if ($this->isCsrfTokenValid('delete'.$item->getId(), $request->request->get('_token'))) {
            $itemsRepository->remove($item);
        }

        return $this->redirectToRoute('app_items_index', [], Response::HTTP_SEE_OTHER);
    }
    /**
     * @Route("/{id}/delete", name="app_items_deleteone", methods={"GET"})
     */
    public function deleteone(Items $items): Response
    {
        $entityManager = $this->getDoctrine()->getManager();
        $entityManager->remove($items);
        $entityManager->flush();

        return $this->redirectToRoute('app_items_index');
    }
    #[Route("/items/{id}/detail", name:"items.detail")]
    
    public function detail(Items $items, Request $request, CartManager $cartManager): Response  
    {
        $form = $this->createForm(AddToCartType::class);
        $form->handleRequest($request);
        if ($form->isSubmitted() && $form->isValid())
        {
            $item = $form->getData();
            $item->setItem($items);

            $cart = $cartManager->getCurrentCart();
            $cart
                ->addItem($item)
                ->setUpdatedAt(new \DateTime());
            $cartManager->save($cart);
            return $this->redirectToRoute('home', ['id' => $items->getId()]);
        }
        return $this->render('items/detail.html.twig', [
            'items'=>$items,
            'form' => $form->createView()
        ]);
    }
    

}
